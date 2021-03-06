/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.test.object;

import org.ballerinalang.launcher.util.BAssertUtil;
import org.ballerinalang.launcher.util.BCompileUtil;
import org.ballerinalang.launcher.util.CompileResult;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for equivalency of user defined object types with attached functions in ballerina.
 */
public class ObjectEquivalencyNegativeTest {

    @Test(description = "Test equivalence of objects that are in the same package")
    public void testEquivalenceOfObjectsInSamePackage() {
        CompileResult compileResult = BCompileUtil.compile("test-src/object/object-equivalency-01-negative.bal");
        Assert.assertEquals(compileResult.getErrorCount(), 7);
        BAssertUtil.validateError(compileResult, 0,
                "incompatible types: 'employee01' cannot be explicitly typed as 'person01'", 24, 18);
        BAssertUtil.validateError(compileResult, 1,
                "incompatible types: 'employee02' cannot be explicitly typed as 'person02'", 51, 18);
        BAssertUtil.validateError(compileResult, 2,
                "incompatible types: 'employee04' cannot be explicitly typed as 'person04'", 108, 18);
        BAssertUtil.validateError(compileResult, 3,
                "incompatible types: 'employee05' cannot be explicitly typed as 'person05'", 145, 18);
        BAssertUtil.validateError(compileResult, 4,
                "incompatible types: 'employee06' cannot be explicitly typed as 'person06'", 175, 18);
        BAssertUtil.validateError(compileResult, 5,
                "incompatible types: 'employee08' cannot be explicitly typed as 'person08'", 306, 18);
        BAssertUtil.validateError(compileResult, 6,
                "incompatible types: 'employee09' cannot be explicitly typed as 'person09'", 375, 18);
    }

    @Test(description = "Test equivalence of objects that are in the same package from a third package")
    public void testEquivalenceOfObjectsInSamePackageFromDifferentPackage() {
        CompileResult compileResult = BCompileUtil.compile("test-src/object/object-equivalency-02-negative.bal");

        Assert.assertEquals(compileResult.getErrorCount(), 2);
        BAssertUtil.validateError(compileResult, 0,
                "incompatible types: 'org.foo.bar:userBar' cannot be explicitly typed as 'org.foo:userFoo'", 11, 23);
        BAssertUtil.validateError(compileResult, 1,
                "incompatible types: 'org.foo.bar:BarObj' cannot be explicitly typed as 'org.foo:FooObj'", 17, 25);
    }
}
