import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import groovy.json.JsonOutput

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

println '----------------------------------------------------'
CustomKeywords.'my.TestCaseIdPrinter.print'()
def l1 = WebUI.callTestCase(findTestCase('Level1'), [:], FailureHandling.OPTIONAL)
assert l1.Level1result.Level2result.Level3result == "Hi"

def rootResult = [ 'RootResult' : l1 ]
String json = JsonOutput.toJson(rootResult)
println JsonOutput.prettyPrint(json)

