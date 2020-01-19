import argparse
import random


parser = argparse.ArgumentParser()

parser.add_argument('--number', help="Number of random line you want", required=True, type=int)
parser.add_argument('--file', help="File to which you want to create / append the lines", required=True, type=str)
parser.add_argument('--name', help="File with name in it", required=True, type=str)
parser.add_argument('--noappend', help="Add this parameter to erase the file, instead of append the lines to", action='store_true')

args = parser.parse_args()

mode = 'w' if args.noappend else 'a'
file = open(args.file, mode)
name_file = open(args.name, 'r')
name_line = name_file.read().splitlines()

base_line = "INSERT INTO public.\"user_entity\"({}) VALUES\n"


def generateLine(k):
    data_line = [
        ('mail', name_line[k].replace('\n', '')),
        ('first_name', '\'FirstName\''),
        ('last_name', '\'LastName\''),
        ('password', '\'$2a$10$bYNdFSjjeQZx7miuM9/AoeCpcKJCTaBczBD8NkQ57peAdNPkOeywC\''),
        ('role', '\'Administrator\'' if random.randint(0, 1) else '\'Collaborator\''),
    ]
    return data_line

file.write("\\connect project_two\n")
file.write(base_line.format(', '.join(map(lambda x: x[0], generateLine(0)))))

lines = []

HAS_FAILED = False

try:
    from tqdm import tqdm
except Exception as e:
    HAS_FAILED = True

for i in range(args.number) if HAS_FAILED else tqdm(range(args.number)):
    data = generateLine(i)
    lines.append(', '.join(map(lambda x: x[1], data)))


file.write('({});'.format('),\n('.join(lines)))

file.close()
print('Writed {} lines to file {}'.format(args.number, args.file))
