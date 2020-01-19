import argparse
import random

parser = argparse.ArgumentParser()

parser.add_argument('--number', help="Number of random line you want", required=True, type=int)
parser.add_argument('--file', help="File to which you want to create / append the lines", required=True, type=str)
parser.add_argument('--unicorns', help="File with name in it", required=True, type=str)
parser.add_argument('--magics', help="File with name of creator in it", required=True, type=str)
parser.add_argument('--noappend', help="Add this parameter to erase the file, instead of append the lines to",
                    action='store_true')

args = parser.parse_args()

mode = 'w' if args.noappend else 'a'
file = open(args.file, mode)
unicorns_file = open(args.unicorns, 'r')
unicorns_line = unicorns_file.read().splitlines()

magics_file = open(args.magics, 'r')
magics_line = magics_file.read().splitlines()

base_line = "INSERT INTO public.unicorn_entity_magic_entities({}) VALUES\n"


def generateLine():
    data_line = [
        ('unicorn_entities_name', unicorns_line[random.randint(0, args.number-1)].replace('\n', '')),
        ('magic_entities_name', magics_line[random.randint(0, args.number-1)].replace('\n', '')),
    ]
    return data_line


i = 0
file.write("\\connect project_two\n")
file.write(base_line.format(', '.join(map(lambda x: x[0], generateLine()))))

lines = []

HAS_FAILED = False

try:
    from tqdm import tqdm
except Exception as e:
    HAS_FAILED = True

for i in range(args.number) if HAS_FAILED else tqdm(range(args.number)):
    data = generateLine()
    lines.append(', '.join(map(lambda x: x[1], data)))

file.write('({});'.format('),\n('.join(lines)))

file.close()
unicorns_file.close()
magics_file.close()
print('Writed {} lines to file {}'.format(args.number, args.file))
