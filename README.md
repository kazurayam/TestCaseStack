# Tracing the Test Case caller-callee stack

## What is this?

This is a small Katalon Studio project for demonstration purpose. You can download the zip file from [Relases](https://github.com/kazurayam/TestCaseStack/releases) page, unzip it and open it with your Katalon Studio.

This project was developed using Katalon Studio version 5.10.1

This project was developed in order to respond to a question raised in the
Katalon Forum at
https://forum.katalon.com/t/get-name-of-test-in-a-custom-keyword/10571/4


## Problem to solve

In a TestListener with the follwing code,
```
class MyTestListener {
    @BeforeTestCase
    def beforeTestCase(TestCaseContext testCaseContext) {
        GlobalVariable.CURRENT_TESTCASE_ID = testCaseContext.getTestCaseId()
    }
}
```
`GlobalVariable.CURRENT_TESTCASE_ID` will have the name of testcase invoked by Katalon Studio.

That's fine. But we want to know more. Here we assume we have a chain like
1. Test Suite `TS1` invokes a Test Case `Root`.
2. `Root` calls another Test Case `Level1`, which calls `Level2`, which calls `Level3`  with the built-in keyword `WebUI.callTestCase()`

Question:
**How the test case code of `Level1` can know the TestCaseId of itself to be `Test Cases/Level1`?**

Question:
**How the test case code of `Level2` can know the TestCaseId of itself to be `Test Cases/Level2`?**

Question:
**How the test case code of `Level3` can know the TestCaseId of itself to be `Test Cases/Level3`?**

## Solution

Katalon Studio does not provide any built-in solution for the above question.

If you really want the solution, you need to change the source of the built-in keyword `WebUI.callTestCase()`.

But how the change would be?

I can show you how to change the source of the built-in keyword `WebUI.callTestCase()` with
a running example by changing the implementation runtime using Groovy's metaprogramming.

## Description

### Running the demo

1. Get the zip file of the demo project from the [Releases](https://github.com/kazurayam/TestCaseStack/releases) page, unzip it, open it with your Katalon Studio.
2. Open the test suite `TS1`, and manually run it.
3. `TS1` invokes a parent test case `Root`.
4. `Root` calls other test case `Level1`, which calls `Level2`, which calls `Level3`. The builtin keyword `WebUI.callTestCase()` is used.
5. In the Console tab, you can see the following output:

```
----------------------------------------------------
top-level TestCaseId : Test Cases/Root
callee TestCaseId    : empty

----------------------------------------------------
top-level TestCaseId : Test Cases/Root
callee TestCaseId    : Test Cases/Level1

----------------------------------------------------
top-level TestCaseId : Test Cases/Root
callee TestCaseId    : Test Cases/Level2

----------------------------------------------------
top-level TestCaseId : Test Cases/Root
callee TestCaseId    : Test Cases/Level3

```

### Notable points

In the Console message, you can find that:

1. `GlobalVariable.CURRENT_TESTCASE_ID` stays unchanged during the course of testcase chain. This proves that the TestListener#beforeTestCase() is NOT invoked for the test cases `Level1`, `Level2` and `Level3` which are invoked by  `WebUI.callTestCase()` by the caller test cases.
2. By my trick, you can see the callee TestCaseIds are printed.

### My trick

My trick is here in the source code of [`Test Listeners/MyTestListener`](Test Listeners/MyTestListener.groovy).

In the method annotated with `@BeforeTestCase`, I do

```
@BeforeTestCase
def beforeTestCase(TestCaseContext testCaseContext) {
    // save the name of top-level test case
    GlobalVariable.CURRENT_TESTCASE_ID = testCaseContext.getTestCaseId()

    // initialize TESTCASE_STACK with java.util.Stack object
    if (GlobalVariable.TESTCASES_STACK == null) {
        GlobalVariable.TESTCASES_STACK = new Stack<String>()
    }

    // modify WebUI.callTestCase() implementation
    // see for the original https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/keyword/BuiltinKeywords.groovy
    BuiltinKeywords.metaClass.'static'.callTestCase = { TestCase calledTestCase, Map binding, FailureHandling flowControl ->
        ((Stack)GlobalVariable.TESTCASES_STACK).push(calledTestCase.getTestCaseId())
        Object result = (Object)KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_BUILT_IN, "callTestCase", calledTestCase, binding, flowControl)
        ((Stack)GlobalVariable.TESTCASES_STACK).pop()
        return result
    }
    BuiltinKeywords.metaClass.'static'.callTestCase = { TestCase calledTestCase, Map binding ->
        ((Stack)GlobalVariable.TESTCASES_STACK).push(calledTestCase.getTestCaseId())
        Object result = (Object)KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_BUILT_IN, "callTestCase", calledTestCase, binding)
        ((Stack)GlobalVariable.TESTCASES_STACK).pop()
        return result
    }
}
```

Here I used Groovy's metaprogromming feature. See [ExpandoMetaClass](http://groovy-lang.org/metaprogramming.html#metaprogramming_emc) for technical detail. I would not talk about it here.

Please note that the `GlobalVariable.TESTCASE_STACK` must be declared with type `Null` in order to initialize it with a Stack object.
