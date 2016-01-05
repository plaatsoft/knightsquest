.rodata
.balign 32
.globl pic900length
.globl pic900data

pic900length:	.long	picdataend - pic900data
pic900data:
.incbin "../images/red.png"
picdataend:


