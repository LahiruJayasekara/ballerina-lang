/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.observe.trace;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.util.tracer.OpenTracerBallerinaWrapper;
import org.ballerinalang.util.tracer.ReferenceType;

import java.util.Collections;

/**
 * This function which implements the startSpan method for tracing.
 */
@BallerinaFunction(
        orgName = "ballerina",
        packageName = "observe",
        functionName = "startSpanWithParentSpan",
        args = {
                @Argument(name = "serviceName", type = TypeKind.STRING),
                @Argument(name = "spanName", type = TypeKind.STRING),
                @Argument(name = "tags", type = TypeKind.MAP),
                @Argument(name = "reference", type = TypeKind.ENUM),
                @Argument(name = "parentSpanContext", type = TypeKind.STRUCT, structType = "Span",
                        structPackage = "ballerina.observe")
        },
        returnType = @ReturnType(type = TypeKind.STRUCT, structType = "Span", structPackage = "ballerina.observe"),
        isPublic = true
)
public class StartSpanWithParentSpan extends BlockingNativeCallableUnit {

    @Override
    public void execute(Context context) {

        String serviceName = context.getStringArgument(0);
        String spanName = context.getStringArgument(1);
        BMap tags = (BMap) context.getRefArgument(0);
        String reference = context.getRefArgument(1).stringValue();
        BStruct parentSpan = (BStruct) context.getRefArgument(2);

        String spanId;
        if (ReferenceType.valueOf(reference) != ReferenceType.ROOT && parentSpan != null) {
            String parentSpanId = parentSpan.getStringField(0);
            spanId = OpenTracerBallerinaWrapper.getInstance().startSpan(serviceName, spanName,
                    Utils.toStringMap(tags), ReferenceType.valueOf(reference), parentSpanId);
        } else {
            spanId = OpenTracerBallerinaWrapper.getInstance().startSpan(serviceName, spanName,
                    Utils.toStringMap(tags), ReferenceType.valueOf(reference), Collections.emptyMap());
        }

        if (spanId != null) {
            context.setReturnValues(Utils.createSpanStruct(context, spanId, serviceName, spanName));
        } else {
            context.setReturnValues(Utils.createSpanStruct(context, null, null, null));
            System.err.println("ballerina: Can not use tracing API when tracing is disabled");
        }
    }
}
