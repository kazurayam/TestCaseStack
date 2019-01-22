import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.keyword.internal.KeywordExecutor
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase

import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.keyword.BuiltinKeywords

class MyTestListener {
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		
		// save the name of top-level test case
		GlobalVariable.CURRENT_TESTCASE_ID = testCaseContext.getTestCaseId()
		
		// initialize TESTCASE_STACK with java.util.Stack object
		if (GlobalVariable.TESTCASE_STACK == null) {
			GlobalVariable.TESTCASE_STACK = new Stack<String>()
		}
		
		// modify WebUI.callTestCases() implementation
		// see for the original https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/keyword/BuiltinKeywords.groovy
		BuiltinKeywords.metaClass.'static'.callTestCase = { TestCase calledTestCase, Map binding, FailureHandling flowControl ->
			((Stack)GlobalVariable.TESTCASE_STACK).push(calledTestCase.getTestCaseId())
			Object result = (Object)KeywordExecutor.executeKeywordForPlatform(
				KeywordExecutor.PLATFORM_BUILT_IN, "callTestCase", calledTestCase, binding, flowControl)
			((Stack)GlobalVariable.TESTCASE_STACK).pop()
			return result
		}
		BuiltinKeywords.metaClass.'static'.callTestCase = { TestCase calledTestCase, Map binding ->
			((Stack)GlobalVariable.TESTCASE_STACK).push(calledTestCase.getTestCaseId())
			Object result = (Object)KeywordExecutor.executeKeywordForPlatform(
				KeywordExecutor.PLATFORM_BUILT_IN, "callTestCase", calledTestCase, binding)
			((Stack)GlobalVariable.TESTCASE_STACK).pop()
			return result
		}
	}

}
