let mysql = require("mysql");
let db = {};

db.connection = function(){
    let connection = mysql.createConnection({
        host:'localhost',
        user:'root',
        password:'liyuhang8',
        database:'PersonDatabase',
        port:3306
    });

    connection.connect(function(err){
        if(err){
            console.log(err);
            return;
        }
    });
    return connection;
}

db.checkUID = function(uid, callback){
    db.connection().query("select TeaId from StreamTeaAccount where TeaUid='"+uid+"';", callback);
}

db.select = function(sql, callback){
    db.connection().query(sql, callback);
}

db.query = function(sql, param, callback){
    db.connection().query(sql, param, callback);
}

module.exports = db;