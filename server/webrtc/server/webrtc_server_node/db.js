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
    let con = db.connection()
    con.query("select TeaId from StreamTeaAccount where TeaUid='"+uid+"';", callback);
    con.end();
}

db.select = function(sql, callback){
    let con = db.connection();
    con.query(sql, callback);
    con.end();
}

db.query = function(sql, param, callback){
    let con = db.connection();
    con.query(sql, param, callback);
    con.end();
}

module.exports = db;