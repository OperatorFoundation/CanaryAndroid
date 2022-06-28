package com.example.canarylibrary

class TestController()
{
    // ignore shared instance from Swift for now..

    fun runSwiftTransportTest(transport: Transport): TestResult?
    {

        return null
    }

    fun runWebTest(webTest: WebTest): TestResult?
    {

        return null
    }

    fun save(result: TestResult, testName: String): Boolean
    {

        return true
    }

    fun test(transport: Transport, userInterface: String?, debugPrints: Boolean = false)
    {
        // TODO: Add a transport test
    }

    fun test(webTest: WebTest, userInterface: String?, debugPrints: Boolean = false)
    {
        // TODO: Add a web test
    }

    fun getNowAsString(): String
    {

        return ""
    }
}