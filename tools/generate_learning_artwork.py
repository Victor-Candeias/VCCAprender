"""Original offline learning vectors. Run from the repository root to regenerate.

The SVG contact sheet and Android drawables share the exact same paths and palette.
No fonts, external images, or network resources are used.
"""
from pathlib import Path
import html
import math

ROOT = Path(__file__).resolve().parents[1]
INK, BLUE, RED, GREEN = '#243B53', '#2864B4', '#D33D43', '#278653'
YELLOW, ORANGE, PURPLE, WHITE = '#FFC44D', '#EB9152', '#8854B8', '#FFFFFF'
PINK, BROWN, GREY, SKY = '#F1A7B8', '#AD754D', '#A8BBCB', '#EAF4FC'
art = {}
paths = []

def path(d, fill, stroke=INK, width=2):
    paths.append((d, fill, stroke, width))

def ellipse(x, y, rx, ry, fill, stroke=INK, width=2):
    path(f'M{x-rx},{y}a{rx},{ry} 0 1,0 {2*rx},0a{rx},{ry} 0 1,0 {-2*rx},0', fill, stroke, width)

def circle(x, y, r, fill, stroke=INK, width=2):
    ellipse(x, y, r, r, fill, stroke, width)

def rect(x, y, w, h, fill, stroke=INK, width=2):
    path(f'M{x},{y}h{w}v{h}h{-w}Z', fill, stroke, width)

def line(d, color=INK, width=3):
    path(d, None, color, width)

def star(x=50, y=50, r=36, fill=YELLOW):
    pts = [(x+(r if i%2 == 0 else r*.45)*math.cos(i*math.pi/5-math.pi/2),
            y+(r if i%2 == 0 else r*.45)*math.sin(i*math.pi/5-math.pi/2)) for i in range(10)]
    path('M'+'L'.join(f'{a:.2f},{b:.2f}' for a,b in pts)+'Z', fill)

def eyes(x=50, y=49, gap=12):
    circle(x-gap, y, 3, INK, None); circle(x+gap, y, 3, INK, None)

def face(fill, ears='round'):
    if ears == 'point':
        path('M23,42L20,13L43,32M57,32L80,13L77,42', fill)
    elif ears == 'long':
        ellipse(35,24,10,21,fill); ellipse(65,24,10,21,fill)
        ellipse(35,23,4,13,PINK,None); ellipse(65,23,4,13,PINK,None)
    elif ears == 'round':
        circle(25,31,13,fill); circle(75,31,13,fill)
    ellipse(50,57,31,29,fill)
    eyes(y=51)
    path('M45,63L55,63L50,69Z',INK,None)
    line('M50,69Q43,77 38,70M50,69Q57,77 62,70',width=2)

def save(key, label):
    global paths
    art[key] = (label, paths)
    paths = []

star(); save('star','Estrela')
for key,label,color in [('red','Vermelho',RED),('blue','Azul',BLUE),('green','Verde',GREEN),
                        ('yellow','Amarelo',YELLOW),('orange','Laranja',ORANGE),('purple','Roxo',PURPLE)]:
    circle(50,50,34,color); save(key,label)
circle(50,50,33,BLUE); save('circle','Círculo')
rect(20,20,60,60,ORANGE); save('square','Quadrado')
path('M50,14L87,80H13Z',GREEN); save('triangle','Triângulo')
rect(10,30,80,40,PURPLE); save('rectangle','Retângulo')
path('M50,83C-12,43 18,3 50,30C82,3 112,43 50,83Z',RED); save('heart','Coração')

face(ORANGE,'point')
line('M15,56L34,61M14,68L34,66M66,61L85,56M66,66L86,68',width=2)
save('cat','Gato')
ellipse(22,47,13,28,BROWN); ellipse(78,47,13,28,BROWN)
face('#EBC69F',ears='none')
ellipse(50,67,15,11,WHITE); circle(50,62,6,INK); eyes(y=49)
save('dog','Cão')
path('M26,30L17,13L34,22M66,22L83,13L74,30',YELLOW)
ellipse(19,42,12,7,WHITE); ellipse(81,42,12,7,WHITE)
ellipse(50,52,29,31,WHITE); ellipse(35,43,10,13,INK,None)
eyes(y=47); ellipse(50,73,23,12,PINK); circle(41,73,3,INK); circle(59,73,3,INK)
save('cow','Vaca')
circle(50,51,40,BROWN); face(YELLOW); save('lion','Leão')
ellipse(22,49,18,28,GREY); ellipse(78,49,18,28,GREY); circle(50,48,29,GREY)
path('M42,56V78Q42,95 61,87L61,77Q53,83 55,72V56Z',GREY); eyes(y=46)
save('elephant','Elefante')
ellipse(28,35,20,23,PURPLE); ellipse(72,35,20,23,PURPLE)
ellipse(30,70,18,19,ORANGE); ellipse(70,70,18,19,ORANGE)
ellipse(50,54,6,30,INK); line('M48,26L38,14M52,26L62,14'); save('butterfly','Borboleta')
path('M68,49L91,27V73Z',ORANGE); ellipse(44,50,33,23,BLUE)
path('M35,28L51,13L58,31M34,71L52,85L59,69',BLUE)
circle(26,45,4,WHITE); circle(26,45,2,INK); save('fish','Peixe')
face(WHITE,'long'); save('rabbit','Coelho')
for x,y in [(29,35),(50,29),(71,35),(24,54),(76,54),(33,71),(66,71)]: circle(x,y,17,WHITE)
ellipse(50,55,21,25,GREY); eyes(y=50,gap=8); save('sheep','Ovelha')
ellipse(47,66,34,21,YELLOW); circle(60,34,19,YELLOW)
path('M75,34L94,41L76,46Z',ORANGE); circle(64,30,3,INK)
line('M29,63Q44,78 61,62'); save('duck','Pato')
path('M39,31Q24,1 42,16Q51,0 56,16Q72,4 66,31',RED)
ellipse(49,62,28,25,WHITE); circle(56,36,19,WHITE)
path('M73,34L92,42L73,48Z',YELLOW); circle(60,31,3,INK)
line('M39,85V94M61,85V94M30,94H46M53,94H70',ORANGE)
path('M24,65Q2,42 11,32Q28,36 29,51',GREEN); save('rooster','Galo')
ellipse(32,32,19,23,WHITE); ellipse(68,32,19,23,WHITE)
ellipse(50,59,29,27,GREY); eyes(y=51); circle(50,68,5,PINK)
line('M18,64L36,66M64,66L82,64M18,74L36,70M64,70L82,74',width=2); save('mouse','Rato')
circle(32,29,13,GREEN); circle(68,29,13,GREEN); ellipse(50,60,34,26,GREEN)
circle(32,28,7,WHITE); circle(68,28,7,WHITE); eyes(y=28,gap=18)
line('M29,60Q50,83 71,60'); save('frog','Sapo')
ellipse(37,33,14,23,WHITE); ellipse(63,33,14,23,WHITE)
ellipse(50,55,27,22,YELLOW)
rect(40,35,7,39,INK,None); rect(55,36,7,37,INK,None)
circle(24,50,13,YELLOW); circle(20,47,3,INK); line('M19,37L14,28M28,38L32,29',width=2)
save('bee','Abelha')
ellipse(22,65,13,9,GREEN); ellipse(68,76,13,8,GREEN)
ellipse(51,55,31,24,GREEN); circle(84,47,12,GREEN); circle(87,44,2,INK)
path('M35,38L50,32L65,40L65,60L50,73L35,62Z',YELLOW); save('turtle','Tartaruga')
ellipse(33,20,8,16,WHITE); ellipse(67,20,8,16,WHITE)
ellipse(50,54,25,35,WHITE)
path('M28,40L40,47L26,50M72,40L60,47L74,50M43,21L48,41L50,20M56,21L53,42L62,25',INK,None)
eyes(y=49,gap=11)
ellipse(50,75,22,14,GREY); circle(41,74,3,INK); circle(59,74,3,INK)
save('zebra','Zebra')

rect(23,42,54,45,ORANGE); path('M12,44L50,12L88,44Z',RED)
rect(42,59,16,28,BLUE); rect(63,51,10,13,WHITE); save('house','Casa')
circle(50,50,35,WHITE); path('M50,34L65,45L59,63H41L35,45Z',INK)
line('M50,34V15M65,45L83,37M59,63L70,80M41,63L30,80M35,45L17,37',width=3)
save('ball','Bola')
path('M12,25Q31,19 50,30Q69,19 88,25V78Q69,71 50,83Q31,71 12,78Z',WHITE)
line('M50,30V83M23,38L40,40M23,49L40,51M23,60L40,62M60,40L77,38M60,51L77,49M60,62L77,60',BLUE,2)
save('book','Livro')
path('M50,32C12,7 2,54 27,82Q39,95 50,85Q66,97 80,77C102,36 72,14 50,32Z',RED)
line('M50,32Q45,16 57,9',BROWN,5); path('M54,20Q61,4 78,12Q75,27 54,20Z',GREEN); save('apple','Maçã')
path('M13,48L26,28H68L82,48H91V77H9V48Z',RED)
path('M31,34H47V48H23ZM53,34H64L74,48H53Z',SKY)
circle(28,77,11,INK); circle(72,77,11,INK); circle(28,77,5,GREY); circle(72,77,5,GREY)
save('car','Carro')
for i in range(8):
    a=i*math.pi/4
    line(f'M{50+29*math.cos(a):.2f},{50+29*math.sin(a):.2f}L{50+42*math.cos(a):.2f},{50+42*math.sin(a):.2f}',ORANGE,4)
circle(50,50,23,YELLOW); save('sun','Sol')
line('M50,48V90',GREEN,5); path('M49,76Q23,79 24,61Q42,59 49,76Z',GREEN)
for i in range(6):
    a=i*math.pi/3
    circle(50+20*math.cos(a),39+20*math.sin(a),13,PINK)
circle(50,39,13,YELLOW); save('flower','Flor')
circle(29,34,19,YELLOW); circle(29,34,8,SKY)
path('M40,46L49,37L89,77L80,86L73,79L80,72L72,64L65,71L58,64L65,57Z',YELLOW); save('key','Chave')
rect(21,21,58,58,WHITE)
for x,y in [(34,34),(66,34),(50,50),(34,66),(66,66)]: circle(x,y,5,INK)
save('die','Dado')
path('M26,87V58Q19,37 28,37L42,53V15Q42,5 52,15V43Q61,33 66,46Q77,39 80,52V71L70,87Z','#EBC69F')
save('finger','Dedo')
path('M28,85V55Q15,31 24,29L36,45V15Q36,6 44,15V39V10Q44,1 52,10V39V15Q52,6 60,15V42V23Q60,14 68,23V52L79,43Q89,39 86,50L77,75L66,85Z','#EBC69F')
save('hand','Mão')
path('M22,82V42Q6,35 18,21Q50,-1 82,21Q94,35 78,42V82Z','#D7A16C')
path('M29,75V38Q16,31 27,25Q50,10 73,25Q84,31 71,38V75Z','#FFE2AB',None); save('bread','Pão')
path('M66,12C10,7 1,77 53,88Q77,92 89,66C53,85 31,40 66,12Z',YELLOW); save('moon','Lua')
path('M34,10H66L91,90H9Z',GREY); line('M50,16V30M50,43V57M50,70V85',WHITE,4)
path('M2,90L29,10H20L0,72ZM98,90L71,10H80L100,72Z',GREEN,None); save('road','Rua')
path('M25,86V51Q8,29 18,26L32,38V14Q32,5 41,14V35V10Q41,1 50,10V35V15Q50,6 59,15V38V24Q59,15 68,24V67L60,86Z',PURPLE)
rect(24,78,39,13,BLUE); save('glove','Luva')
path('M21,33H79L87,86H13Z',ORANGE); path('M34,34V24Q50,5 66,24V34',None)
save('bag','Saco')
path('M23,18H77L70,87H30Z',WHITE); path('M27,43H73L68,82H32Z','#D4E8F4',None)
line('M34,27L44,28',WHITE,3); save('milk','Leite')
path('M50,19C12,-1 15,41 25,57C24,106 42,92 48,63Q50,54 54,64C61,95 77,106 76,56C91,11 68,9 50,19Z',WHITE)
line('M39,27Q49,32 59,26',BLUE,2); save('tooth','Dente')
rect(21,46,7,43,BROWN); rect(72,46,7,43,BROWN); rect(12,31,76,17,ORANGE)
save('table','Mesa')
path('M35,25V14H65V25',None); rect(15,25,70,58,ORANGE)
rect(29,25,7,58,BROWN); rect(64,25,7,58,BROWN); circle(28,88,4,INK); circle(73,88,4,INK)
save('suitcase','Mala')
rect(18,44,64,41,PINK); path('M18,44Q28,35 38,44Q48,35 58,44Q68,35 82,44V55Q72,66 62,55Q52,66 42,55Q32,66 18,55Z',WHITE)
for x in [33,50,67]:
    rect(x-2,25,4,16,BLUE); path(f'M{x},12Q{x+9},24 {x},25Q{x-9},24 {x},12Z',YELLOW)
save('cake','Bolo')

ellipse(51,57,29,20,GREEN); path('M25,53L7,40V58L26,63Z',GREEN)
path('M53,39V19M22,19H84M29,79H76M36,73V79M68,73V79',None)
path('M55,44Q72,40 77,57H55Z',SKY); save('helicopter','Helicóptero')
ellipse(50,80,44,12,BLUE); ellipse(50,72,32,10,YELLOW)
line('M50,72Q59,42 53,29',BROWN,6)
for d in ['M53,30Q25,10 20,40Q37,28 53,30Z','M53,30Q77,10 85,36Q67,27 53,30Z','M53,30Q59,5 70,13L53,30Z']:
    path(d,GREEN)
save('island','Ilha')
rect(18,15,64,72,BROWN); rect(25,22,50,58,SKY); line('M50,22V80M25,51H75',BROWN,5)
save('window','Janela')
ellipse(50,50,29,39,BROWN); ellipse(50,50,24,33,GREEN); ellipse(50,50,9,14,'#EAF6BC',None)
for i in range(10):
    a=i*math.pi/5
    circle(50+16*math.cos(a),50+22*math.sin(a),1.8,INK,None)
save('kiwi','Kiwi')
path('M10,63H90L77,85H25Z',BLUE); rect(28,37,46,26,WHITE); rect(39,22,12,15,RED)
for x in [38,52,66]: circle(x,50,4,SKY)
line('M8,91Q18,83 29,91Q40,83 51,91Q62,83 73,91Q84,83 94,91',BLUE)
save('ship','Navio')
path('M14,42L65,15L86,42V82H14Z',YELLOW); line('M14,42H86',ORANGE,2)
for x,y,r in [(32,59,7),(64,66,8),(69,40,4),(48,32,4)]: circle(x,y,r,ORANGE)
save('cheese','Queijo')
line('M50,28L59,10',BROWN); path('M53,21Q72,6 82,24Q67,35 53,21Z',GREEN)
for x,y in [(33,35),(51,35),(69,35),(40,52),(60,52),(50,69),(50,82)]: circle(x,y,11,PURPLE)
save('grapes','Uva')
rect(17,17,66,66,'#E3AD62')
for x in [26,44,62]:
    for y in [26,44,62]: rect(x,y,12,12,'#B87B35',None)
save('waffle','Waffle')
line('M24,32L76,77M19,60L77,27',BROWN,5)
for i,color in enumerate([RED,ORANGE,YELLOW,GREEN,BLUE,PURPLE]):
    x=14+i*12
    rect(x,25+i*4,10,49-i*4,color)
circle(19,60,5,YELLOW); circle(77,27,5,YELLOW); save('xylophone','Xilofone')
circle(50,21,11,'#EBC69F'); path('M39,37H61L67,65H33Z',PURPLE)
line('M38,42L20,59L9,47M62,42L80,59L91,47','#EBC69F',7)
path('M34,64Q8,69 17,83Q33,95 51,79Q67,95 84,83Q92,69 66,64L50,70Z',BLUE)
save('yoga','Yoga')

# Letter A drawn as paths, avoiding dependence on an installed typeface.
line('M22,79L43,20L64,79M31,57H55',BLUE,8); circle(78,31,8,ORANGE)
save('letters','Letras')
path('M15,40H32L53,21V79L32,60H15Z',BLUE)
line('M65,37Q80,50 65,63M76,25Q100,50 76,75',BLUE,4); save('sound','Som')

out = ROOT / 'app/src/main/res/drawable'
out.mkdir(parents=True, exist_ok=True)
sheet = []
for index,(key,(label,items)) in enumerate(art.items()):
    xml = ['<vector xmlns:android="http://schemas.android.com/apk/res/android"',
           '    android:width="160dp" android:height="160dp"',
           '    android:viewportWidth="100" android:viewportHeight="100">']
    svg = []
    for d,fill,stroke,width in items:
        attrs = f'android:fillColor="{fill or "#00000000"}"'
        if stroke:
            attrs += f' android:strokeColor="{stroke}" android:strokeWidth="{width}" android:strokeLineJoin="round" android:strokeLineCap="round"'
        xml.append(f'    <path {attrs} android:pathData="{d}" />')
        svg.append(f'<path fill="{fill or "none"}" stroke="{stroke or "none"}" stroke-width="{width}" stroke-linejoin="round" stroke-linecap="round" d="{d}"/>')
    xml.append('</vector>')
    (out/f'learn_{key}.xml').write_text('\n'.join(xml)+'\n', encoding='utf-8')
    x,y = (index%8)*130,(index//8)*140
    sheet.append(f'<g transform="translate({x+15},{y+6})">'+''.join(svg)+f'<text x="50" y="119" text-anchor="middle" font-family="sans-serif" font-size="12">{html.escape(label)}</text></g>')
preview = ROOT/'design/branding/learning-artwork.svg'
preview.write_text(f'<svg xmlns="http://www.w3.org/2000/svg" width="1040" height="{math.ceil(len(art)/8)*140}" viewBox="0 0 1040 {math.ceil(len(art)/8)*140}"><rect width="100%" height="100%" fill="{SKY}"/>'+''.join(sheet)+'</svg>\n',encoding='utf-8')
print(f'Generated {len(art)} original vectors and {preview.relative_to(ROOT)}')
