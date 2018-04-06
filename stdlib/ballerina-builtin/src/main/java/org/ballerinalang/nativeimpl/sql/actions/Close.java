/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.nativeimpl.sql.actions;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.nativeimpl.sql.Constants;
import org.ballerinalang.nativeimpl.sql.SQLDatasource;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;

/**
 * {@code Close} is the Close action implementation of the SQL Connector Connection pool.
 *
 * @since 0.8.4
 */
@BallerinaFunction(
        orgName = "ballerina", packageName = "sql",
        functionName = "close",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = Constants.SQL_CLIENT),
        returnType = {
                @ReturnType(type = TypeKind.STRUCT, structType = "SQLConnectorError",
                            structPackage = "ballerina.sql")
        }
)
public class Close extends AbstractSQLAction {

    @Override
    public void execute(Context context) {
        try {
            BStruct bConnector = (BStruct) context.getRefArgument(0);
            SQLDatasource datasource = (SQLDatasource) bConnector.getNativeData(Constants.SQL_CLIENT);
            closeConnections(datasource);
            context.setReturnValues(null);
        } catch (Throwable e) {
            context.setReturnValues(SQLDatasourceUtils.getSQLConnectorError(context, e));
            SQLDatasourceUtils.handleErrorOnTransaction(context);
        }
    }
}
