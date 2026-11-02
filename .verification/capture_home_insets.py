from pathlib import Path
import re
import subprocess
import time
import xml.etree.ElementTree as ET

ADB = str(Path.home()/'AppData/Local/Android/Sdk/platform-tools/adb.exe')

def adb(*args):
    return subprocess.check_output([ADB, '-s', 'emulator-5554', *args])

def nodes():
    adb('shell', 'uiautomator', 'dump', '/sdcard/window.xml')
    return list(ET.fromstring(adb('exec-out', 'cat', '/sdcard/window.xml')).iter('node'))

def tap(node):
    x1,y1,x2,y2=map(int,re.findall(r'\d+',node.attrib['bounds']))
    adb('shell','input','tap',str((x1+x2)//2),str((y1+y2)//2))
    time.sleep(1)

adb('shell','cmd','overlay','disable','com.android.internal.display.cutout.emulation.tall')
adb('shell','cmd','overlay','enable-exclusive','--category','com.android.internal.systemui.navbar.threebutton')
adb('shell','settings','put','system','user_rotation','0')
time.sleep(3)
adb('shell','am','start','-W','-n','com.vcc.aprender/.MainActivity')
time.sleep(2)
current=nodes()
if not any(n.get('text')=='Mudar de perfil' for n in current):
    entry=next((n for n in current if n.get('content-desc','').startswith('Entrar no perfil de ')),None)
    if entry is None:
        tap(next(n for n in current if n.get('text')=='Adicionar criança'))
        tap(next(n for n in nodes() if n.get('class')=='android.widget.EditText'))
        adb('shell','input','text','Teste')
        adb('shell','input','keyevent','4')
        tap(next(n for n in nodes() if n.get('text')=='Guardar'))
        current=nodes()
        entry=next((n for n in current if n.get('content-desc','').startswith('Entrar no perfil de ')),None)
    if entry is not None:
        tap(entry)
time.sleep(2)
current=nodes()
assert any(n.get('text')=='Mudar de perfil' for n in current), 'Ecrã inicial não encontrado'
target=Path('.verification/home-system-bars-fixed.png')
target.write_bytes(adb('exec-out','screencap','-p'))
print(target)
