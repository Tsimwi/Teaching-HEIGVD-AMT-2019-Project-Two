import random

for j in range(1000000):
    print("\'{}\'".format(''.join([chr(random.randint(97, 122)) for i in range(16)])))
