import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

println '----------------------------------------------------'
CustomKeywords.'my.TestCaseIdPrinter.print'()
def lv3 = WebUI.callTestCase(findTestCase('Level3'),[:],FailureHandling.OPTIONAL)
return ["Level2result": lv3]