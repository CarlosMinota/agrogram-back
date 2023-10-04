package com.springboot.app.auth;

public class JwtConfig {

public static final String LLAVE_SECRETA = "alguna.clave.12345678";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEowIBAAKCAQEAyMh0gEDxnWly47awxACc3f7klrMjpEY9qi3Ll/LYSxrKhvTE\r\n"
			+ "sqSk3a8ydHS+c9Mj84BSBxY2PylIQpUI5EQ3otTLQd+H2tOzR2a7hnc4agEf3l+w\r\n"
			+ "kAaDjlXqQr0emm/IeGq6NRaVILuAexkFvkUiEtjzUL3KbEwxdJY963RUR3xSq58A\r\n"
			+ "40Uu8feLkA/XPe0HxEy71BEBG7msLNdqWyTAbXdUoK0Neo+qSbnrMryK9R34XN7P\r\n"
			+ "Prp5nxbkZHVJFi5TFqlUDmgYnMU6i7LzQ45nuPlN1Cq343AqCNnXkF+BfQp1i6bQ\r\n"
			+ "9M/Z2ai5jd5NNn+GEqxiXHZUAUv2yybCzUy95wIDAQABAoIBAGGwFe6oqmWZ/ooI\r\n"
			+ "Quy6F5hzAajIs9ijTDFWYq4lO1MfT9ZllKYOMSCisvuyKU+VNPKuRjqodfA5F609\r\n"
			+ "cbwN7LvOqK3+Ic04HwvoUejUbKA2t9ir2e3ZHXzNFFMwTIl7RaBA3SQjPp9UWuiJ\r\n"
			+ "fh/a5v8gixGHpcsXLyC4UJwUV0UHu+L1U0mNoqlto0dK81YgmrOWtffqZa/2y8nZ\r\n"
			+ "lvHPHJ/P+IzhkFjaxYAn4WMPpRhm9MagBfimuJVKfbGhsMTJwLa/OSAEFXpNFQbK\r\n"
			+ "CR4XokEUn3twkhb0cIW9wWRDrtOhZqZwe3ISiCgxDEU1ADE8pO3T2NTvDaTcaEwi\r\n"
			+ "Tap3S6ECgYEA/nihJ6OP/jQ5JohIwcStEFytvaTqvFwZ5h25Nf56Ror7XPQp/bnz\r\n"
			+ "OOTvt1zaXZOTdDFw8eIoLxQqxugEV3h+CXD0Qb0LwcmIfKFvVrTid5N/CdS5EEKi\r\n"
			+ "Dvrv1VvEu2/KwPzEz/fJn/pQ+y7Cxq5QJPtv+dtAYz7lpljRcmQsTc8CgYEAyf1B\r\n"
			+ "JHQoxLdBFBxUPNpx0UBVdFgXqODb0EQo8L/6scW8sO9fjJgCb3m3pN7iILpBuDLG\r\n"
			+ "5ahjVHti6h49D01JOHIuJNZ/UNlJTdIfIV2L7uuZs3uEfmGJFHgtrj4BZwv6fxrg\r\n"
			+ "Ygy6e34O/BfmURd5DGKeSaLyo9yzcZmzYPm67GkCgYBXu7k8FF05tEGb8lK7U5aj\r\n"
			+ "VICmKKyxgkN/nfHUFseLeLFPC/NKQgkELZIkMsQqjQ3CeqVJD8Lw8FALQcT1b1ps\r\n"
			+ "4N+lporeJiCDb5kWWMjXlyeueQPRAlgjOArmEXyTbuiFfuBQH0sJd+DhqO6IV2LU\r\n"
			+ "TUf+6cOaM/4zSt3zRWws6wKBgQChyKNAm3l79nu9q+McyqqXO7w2b8ijj1cAvVc9\r\n"
			+ "XcdiVEsKPPB/H9Hzj74z5AGizQyOiL0efXzx0dIsSwQFGZYoC+xBvTxEeYVpO3+L\r\n"
			+ "re3C9Bor1CcPQvgU2R1djnR5IPMGk3RdlGxZNWKzMEFdejuOW/8EY+uMq91ehAlV\r\n"
			+ "BzFc4QKBgH1b5ijs7kUYZ7be4gH/fKxdqPBpi4nJDTkqQndoChZGczuiQKnk624B\r\n"
			+ "2nVgBnxYddpA7UKG9mD/bUgAubPRWihxvB/cjdLffAMc9SeCoEkKFrB6NZmLCeXt\r\n"
			+ "byQOs1vVdlZoWc5qQ1Ek4X2dEnXq69mbA4COFCW+slkZP8llpMqE\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public final static String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyMh0gEDxnWly47awxACc\r\n"
			+ "3f7klrMjpEY9qi3Ll/LYSxrKhvTEsqSk3a8ydHS+c9Mj84BSBxY2PylIQpUI5EQ3\r\n"
			+ "otTLQd+H2tOzR2a7hnc4agEf3l+wkAaDjlXqQr0emm/IeGq6NRaVILuAexkFvkUi\r\n"
			+ "EtjzUL3KbEwxdJY963RUR3xSq58A40Uu8feLkA/XPe0HxEy71BEBG7msLNdqWyTA\r\n"
			+ "bXdUoK0Neo+qSbnrMryK9R34XN7PPrp5nxbkZHVJFi5TFqlUDmgYnMU6i7LzQ45n\r\n"
			+ "uPlN1Cq343AqCNnXkF+BfQp1i6bQ9M/Z2ai5jd5NNn+GEqxiXHZUAUv2yybCzUy9\r\n"
			+ "5wIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";
}
