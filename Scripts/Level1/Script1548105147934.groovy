import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

println '----------------------------------------------------'
CustomKeywords.'my.TestCaseIdPrinter.print'()
WebUI.callTestCase(findTestCase('Level2'),[:],FailureHandling.OPTIONAL)
