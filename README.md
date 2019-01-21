# Tracing the stack of TestCaseIds

## What is this?


This project was developed in order to respond to a question raised in the
Katalon Forum at
https://forum.katalon.com/t/get-name-of-test-in-a-custom-keyword/10571/4

## Problem to solve

## Solution


## Description

### Running the demo


```
----------------------------------------------------
GlobalVariable.CURRENT_TESTCASE_ID: Test Cases/Root
GlobalVariable.TESTCASES_STACK.peek(): empty
...
----------------------------------------------------
GlobalVariable.CURRENT_TESTCASE_ID: Test Cases/Root
GlobalVariable.TESTCASES_STACK.peek(): Test Cases/Level1
...
----------------------------------------------------
GlobalVariable.CURRENT_TESTCASE_ID: Test Cases/Root
GlobalVariable.TESTCASES_STACK.peek(): Test Cases/Level2
...
-------------------------------------------
GlobalVariable.CURRENT_TESTCASE_ID: Test Cases/Root
GlobalVariable.TESTCASES_STACK.peek(): Test Cases/Level3

```
