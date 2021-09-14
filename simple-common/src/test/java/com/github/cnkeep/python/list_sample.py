
def list_sample1():
    list1 = list(range(0,5))
    print(list1)
    list2 = list1[2:]
    list2[0] = 1111
    print(list1)
    print(list2)

if __name__ == '__main__':
    print('hello')
    list_sample1()
