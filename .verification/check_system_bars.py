from pathlib import Path
import json
import subprocess
import time

ADB = str(Path.home() / 'AppData/Local/Android/Sdk/platform-tools/adb.exe')
OUT = Path('.verification')

def adb(*args):
    result = subprocess.run([ADB, '-s', 'emulator-5554', *args], capture_output=True, text=True)
    if result.returncode:
        raise RuntimeError(result.stdout + result.stderr)
    return result.stdout

adb('install', '-r', 'app/build/outputs/apk/debug/app-debug.apk')
adb('install', '-r', 'app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk')
adb('shell', 'settings', 'put', 'system', 'accelerometer_rotation', '0')
results = []
for name, rotation, navigation, cutout in [
    ('portrait-three-buttons', '0', 'threebutton', False),
    ('landscape-three-buttons', '1', 'threebutton', False),
    ('portrait-gestures-cutout', '0', 'gestural', True),
]:
    adb('shell', 'settings', 'put', 'system', 'user_rotation', rotation)
    adb('shell', 'cmd', 'overlay', 'enable-exclusive', '--category',
        'com.android.internal.systemui.navbar.' + navigation)
    if cutout:
        adb('shell', 'cmd', 'overlay', 'enable-exclusive', '--category',
            'com.android.internal.display.cutout.emulation.tall')
    time.sleep(3)
    output = adb('shell', 'am', 'instrument', '-w', '-r', '-e', 'class',
                 'com.vcc.aprender.SystemBarsUiTest',
                 'com.vcc.aprender.test/androidx.test.runner.AndroidJUnitRunner')
    (OUT / f'insets-{name}.txt').write_text(output, encoding='utf-8')
    passed = 'OK (1 test)' in output
    results.append({'configuration': name, 'passed': passed})
    print(name, 'PASS' if passed else 'FAIL', flush=True)
    if not passed:
        print(output[-4000:], flush=True)
        break
(OUT / 'system-bars-results.json').write_text(json.dumps(results, indent=2), encoding='utf-8')
if not all(row['passed'] for row in results) or len(results) != 3:
    raise SystemExit(1)
