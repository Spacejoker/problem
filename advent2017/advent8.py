reg = {}
f = open('input8.txt', 'r')

maxval = 0
for line in f.readlines():
    split = line.split(' ')
    if split[0] not in reg:
        reg[split[0]] = 0
    modval = int(split[2])
    if split[1] == 'dec':
        modval *= -1
    operand = split[4]
    operator = split[5]
    rOperand = int(split[6])
    condition = False

    if (operator == '>'):
        condition = reg.get(operand, 0) > rOperand
    if (operator == '<'):
        condition = reg.get(operand, 0) < rOperand
    if (operator == '>='):
        condition = reg.get(operand, 0) >= rOperand
    if (operator == '<='):
        condition = reg.get(operand, 0) <= rOperand
    if (operator == '!='):
        condition = reg.get(operand, 0) != rOperand
    if (operator == '=='):
        condition = reg.get(operand, 0) == rOperand

    if condition:
        reg[split[0]] += modval

    maxval = max(reg[split[0]], maxval)

print('max at any point in time: %d' % maxval)
print('max at the end %d' % reg[max(reg, key=reg.get)])
