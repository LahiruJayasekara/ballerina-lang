import ballerina/http;

xmlns "http://sample.com/wso2/a1" as ns;

string name = "";

function printName(any name) {

    xmlns "http://sample.com/wso2/a2" as ns;
    var name = formatName(name);

    if (<string>name == ""){
        xmlns "http://sample.com/wso2/a3" as ns;
        var getName = function () returns (string) {
            string name = "Ballerina";
            return "";
        };
        name = getName.call();
    }

    xmlns "http://sample.com/wso2/a3" as ns;
}

function formatName(any a) returns any {
    xmlns "http://sample.com/wso2/a3" as ns;
    return a;
}

type User object {

    public string name = "";


    function f(string name) {
        xmlns "http://sample.com/wso2/a4" as ns;
        string name = "";
        xmlns "http://sample.com/wso2/a4" as ns;
    }
};

service ser = service {

    string name = "";

    resource function res(string name) {
        xmlns "http://sample.com/wso2/a6" as ns;
        string name = "";
        xmlns "http://sample.com/wso2/a7" as ns;
    }
};
