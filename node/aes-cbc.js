/**
 *	加密,解密方法
 *	
 */
var crypto=require('crypto');
/**
 * 加密解密方式
 */
var algorithm='aes-256-cbc';

var key='9875cce6826dbc1fc9083c12c6d75642';
var charset='utf8';
var cipherEncoding='base64';
var iv='0123456789ABCDEF';


var encrypt=function(plaintext){
	//加密
	var cipher=crypto.createCipheriv(algorithm,key,iv);
	var cipherChunks=[];
	cipherChunks.push(cipher.update(plaintext,charset,cipherEncoding));
	cipherChunks.push(cipher.final(cipherEncoding));
	var encrypt=cipherChunks.join('');
	encrypt=encrypt.replace(/\+/g,'%2b');
	return encrypt;
};

var decrypt=function(encrypttext){
	encrypttext=encrypttext.replace(/(%2b)/g,'+');
	//解密start
	var decipher=crypto.createDecipheriv(algorithm,key,iv);
	var plainChunks=[];
	plainChunks.push(decipher.update(encrypttext,cipherEncoding,charset));
	plainChunks.push(decipher.final(charset));
	return  plainChunks.join('');
}

exports.encrypt=encrypt;
exports.decrypt=decrypt;

