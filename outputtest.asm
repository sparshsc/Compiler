.data
	nl: .asciiz "\n"
.text
.globl main
main:
	li $v0 1
	move $a0 $v0
	li $v0 1   #prints value in a0
	syscall
	la $a0 nl
	li $v0 4   #prints new line
	syscall
	li $v0 2
	move $a0 $v0
	li $v0 1   #prints value in a0
	syscall
	la $a0 nl
	li $v0 4   #prints new line
	syscall
	li $v0 3
	move $a0 $v0
	li $v0 1   #prints value in a0
	syscall
	la $a0 nl
	li $v0 4   #prints new line
	syscall
	li $v0 10
	syscall   # end
