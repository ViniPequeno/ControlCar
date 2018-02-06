create database if not exists ControlCar charset= utf8 collate = utf8_general_ci;
use controlcar;
create table if not exists setor(
	codigo bigint not null, 
    nome varchar(10) unique, 
    ramal varchar(6) unique,
    ativo char(1),
    primary key (codigo)
);


create table if not exists funcionario(
	matricula bigint not null,
    nome varchar(30) not null unique, 
    cargo enum('G', 'P'),
    senha varchar(100) char set latin1 collate latin1_swedish_ci,
    email varchar(30), 
    telFixo varchar(12),
    telCelular varchar(12),
    imagem longblob,
    ativo char(1),
    primary key(matricula)
);

create table if not exists veiculo(
    placa varchar(7) not null,
    cor varchar(10),
    marca varchar(10),
    modelo varchar(10),
    anoFabri varchar(4),
    ativo char(1),
    primary key(placa)
);

create table if not exists cadastra(
	matProp bigint not null,
	matFun bigint not null, 
    placa varchar(7) not null, 
    calendario varchar(10) not null,
    ativo char(1) not null,
    primary key(matProp, placa),
    foreign key(matFun) references funcionario(matricula),
    foreign key(placa) references veiculo(placa),
    foreign key(matProp) references funcionario(matricula)
);

create table if not exists registra(
	numero bigint auto_increment not null,
    matProp bigint not null,
	matFunEntrada bigint not null,
	matFunSaida bigint,
    placa varchar(7) not null, 
    entradaHora varchar(5) not null,
    entradaData varchar(10) not null,
    saidaHora varchar(5),
    saidaData varchar(10), 
	obs varchar(100),
	ativo char(1),
    primary key(numero),
    foreign key(matFunEntrada) references funcionario(matricula),
    foreign key(matFunSaida) references funcionario(matricula),
    foreign key(matProp) references funcionario(matricula),
    foreign key(placa) references veiculo(placa)
    
);


create table if not exists lotado(
	matFunc bigint not null, 
    codSetor bigint not null, 
    dataInicio varchar(10) not null,
    dataFim varchar(10) null,
    primary key(matFunc, codSetor, dataInicio),
    foreign key(matFunc) references funcionario(matricula),
    foreign key(codSetor) references setor(codigo)
);



/* ////////////////////////////////////////////////////////////// */
delimiter $$
create procedure adicionarCadastra (in matprop int, in matfun int,in placa varchar(7),
in calendario varchar(10) )
begin 
insert into cadastra values(matprop,matfun,placa,calendario, 'S');
end $$
delimiter ;
delimiter $$
create procedure excluirCadastra (in placa1 varchar(7), in mat bigint)
begin 
update  cadastra set ativo = 'N' where  placa = placa1 and matProp = mat;
end $$
delimiter ;


delimiter $$
create procedure procurarEntrada(in placa1 varchar(7))
begin
select * from cadastra where placa = placa1 and ativo='S';
end $$
delimiter ;

delimiter $$
create procedure procurarSaida(in placa1 varchar(7))
begin
select * from cadastra where placa = placa1 ;
end $$
delimiter ;

delimiter $$
create procedure readmitirCadastra(in placa1 varchar(7), in mat bigint)
begin
update  cadastra set ativo = 'S'
where  placa = placa1 and matProp = mat;
end $$
delimiter ;

	
/* ////////////////////////////////////////////////////////////// */

delimiter &&
create procedure adicionaFunc(in matricula1 bigint, in nome1 varchar(30), 
cargo1 Enum('G', 'P'), email1 varchar(30), telFixo1 varchar(12),
telCelular1 varchar(12), imagem1 longblob)
begin
insert into funcionario values (matricula1, nome1, cargo1,aes_encrypt('123','123'), 
email1, telFixo1, telCelular1, imagem1, 'S');
end &&
delimiter ;
delimiter &&
create procedure excluirFunc(in mat bigint)
begin 
update funcionario set ativo='N' where matricula = mat;
update cadastro set ativo='N' where matProp = mat;
end&&
delimiter ;
delimiter &&
create procedure alterarFunc(in matricula1 bigint, in nome1 varchar(30), cargo1 Enum('G', 'P'), 
senha1 varchar(100), email1 varchar(30), telFixo1 varchar(12),telCelular1 varchar(12), imagem1 longblob)
begin
update funcionario f set f.nome = nome1, f.cargo = cargo1, f.senha = aes_encrypt(senha1,'123'), f.email = email1,
f.telFixo = telFixo1, f.telCelular = telCelular1, f.imagem = imagem1
where f.matricula = matricula1;
end&&
delimiter ;    
delimiter &&
create procedure procurarFunc(in matricula1 bigint)
begin
select matricula, nome, cargo, aes_decrypt(senha, '123'), email, telFixo, telCelular, imagem, ativo
from funcionario where matricula = matricula1 and ativo='S';
end &&
delimiter ;  
   
delimiter $$
create procedure procurarFuncNome(in nome1 varchar(30))
begin
select  matricula, nome, cargo, aes_decrypt(senha, '123'), email, telFixo, telCelular, imagem, ativo
from funcionario
where nome like concat('%', nome1, '%') and ativo='S' and cargo is not null;
end $$
delimiter ;
delimiter &&
create procedure listarFunc()
begin
select * from funcionario where ativo='S' and cargo is not null;
end &&
delimiter ;
delimiter &&
create procedure readmitirFunc(in matricula bigint)
begin
update funcionario f set ativo='S' where f.matricula = matricula;
end &&
delimiter ;
delimiter &&
create procedure inserirSenha(in matricula bigint, in senha1 varchar(100))
begin 
update funcionario f set senha=aes_encrypt(senha1, "123") where f.matricula = matricula;
end &&
delimiter ;

delimiter %%
create procedure procurarFuncGeral(in matricula1 bigint)
begin
select * from funcionario where matricula=matricula1;
end %%
delimiter ;

		
/* ////////////////////////////////////////////////////////////// */
delimiter %%
create procedure inserirLotado(in matFunc1 bigint , in codSetor1 bigint, dataInicio1 varchar(10))
begin 
insert into lotado(matFunc, codSetor, dataInicio) values (matFunc1, codSetor1, dataInicio1);
end %%
delimiter ;
delimiter %% 
create procedure excluirLotado(in matFunc1 bigint)
begin
delete from lotado where matFunc=matFunc1;
end %%
delimiter ;
delimiter %%
create procedure procuraLotado(in matFunc1 bigint, out codSetor1 bigint, out dataInicial varchar(10))
begin
select codSetor, dataInicio into codSetor1, dataInicial from lotado where matFunc= matFunc1 and dataFim is null;
end %%
delimiter ;
delimiter %%
create procedure trocarLotado(in matFunc1 bigint, in dataAtual varchar(10), in codSetorNovo bigint)
begin 
update lotado set dataFim = dataAtual where matFunc = matFunc1;
insert into lotado(matFunc, codSetor, dataInicio) values (matFunc1, codSetorNovo, dataAtual);
end %%
delimiter ;
delimiter %%
create procedure listarLotado()
begin 
select * from lotado l, funcionario f where dataFim is null and f.matricula=l.matFunc and f.ativo='S';
end%%
delimiter ; 
delimiter %%
create procedure listarLotadoNome(in nome varchar(30))
begin 
select * from lotado l, funcionario f where dataFim is null and f.nome like concat('%', nome, '%') and f.ativo ='S' and f.matricula = l.matFunc;
end%%
delimiter ; 


/* ////////////////////////////////////////////////////////////// */

delimiter $$	
create procedure inserirRegistroEntrada (in matProp1 bigint, in matFuncEntrada1 bigint, 
in placa1 varchar(7), in entradaHora1 varchar(5), in entradaData1 varchar(10))
begin 										
insert into registra (matProp, matFunEntrada, placa, entradaHora, entradaData, ativo) 
values (matProp1, matFuncEntrada1, placa1, entradaHora1, entradaData1, 'N');
update veiculo v set ativo = 'S' where v.placa = placa1;
end $$
delimiter ;   

delimiter $$	
create procedure inserirRegistroSaida (in placa1 varchar(7), in matFuncSaida1 bigint, in saidaHora1 varchar(5), in saidaData1 varchar(10))
begin 										
update registra
set registra.matFunSaida = matFuncSaida1, registra.saidaHora = saidaHora1, registra.saidaData = saidaData1
where registra.placa = placa1;
update veiculo v set ativo = 'N' where v.placa = placa1;
end $$
delimiter ;


delimiter $$	
create procedure registrarObservacao (in num bigint, in obs1 varchar(100))
begin 										
update registra
set registra.obs = obs1, ativo='S'
where registra.numero = num;
end $$
delimiter ;








delimiter $$	
create procedure excluirObservacao (in num bigint)
begin 										
update registra
set registra.obs = obs, ativo='N'
where registra.numero = num;
end $$
delimiter ;
delimiter $$
create procedure listarVeiculosFora()
begin
select * from veiculo where ativo='N' and placa in (select v.placa from veiculo v, cadastra c where v.placa = c.placa and c.ativo='S');
end $$
delimiter ;
delimiter $$
create procedure listarVeiculosDentro()
begin
select * from veiculo where ativo='S';
end $$
delimiter ;
delimiter $$
create procedure listarObs()
begin
select * from registra where ativo='S' and obs is not null;
end $$
delimiter ;
delimiter $$
create procedure listarObsFiltrado(in placa1 varchar(7))
begin
select * from registra where ativo='S' and obs is not null and placa=placa1;
end $$
delimiter ;






delimiter $$
create procedure procurarRegistraInicio(in placa1 varchar(7), in dataAtual varchar(10), in horaAtual varchar(50))
begin
select * from registra where placa=placa1 and entradaData = dataAtual and entradaHora=horaAtual;
end$$
delimiter ;
delimiter $$
create procedure procurarRegistraFinal(in placa1 varchar(7), in dataAtual varchar(10), in horaAtual varchar(50))
begin 
select * from registra where placa=placa1 and saidaData = dataAtual and saidaHora=horaAtual;
end$$
delimiter ;
delimiter &&
create procedure procurarRegistra(in placa1 varchar(7))
begin
select * from registra where placa = placa1 and saidaHora is null and saidaData is null;
end&&
delimiter ;

delimiter &&
create procedure procurarRegistraNumero(in num bigint)
begin
select * from registra where registra.numero = num;
end&&
delimiter ;

delimiter &&
create procedure listarEntradas()
begin 
select * from registra r, funcionario f, funcionario f1, veiculo v where r.matProp = f.matricula 
and f1.matricula=r.matFunEntrada and v.placa = r.placa order by r.entradaData desc, r.entradaHora desc, v.placa asc, f.nome asc;
end &&
delimiter ; 

/* ////////////////////////////////////////////////////////////// */
DELIMITER $$
CREATE PROCEDURE alterarServ (in matricula1 bigint, in nome1 varchar(30),
in email1 varchar(30), in telFixo1 varchar(12),
in telCelular1 varchar(12))
begin
update funcionario f set f.nome = nome1, f.email = email1,
f.telFixo = telFixo1, f.telCelular = telCelular1
where f.matricula = matricula1 and senha is null and cargo is null;
end$$
DELIMITER ;
DELIMITER $$
CREATE PROCEDURE inserirServidor(in mat bigint, in nome1 varchar(30), in email1 varchar(30), 
in telFixo1 varchar(12), in telCelular1 varchar(12))
begin
insert into funcionario(matricula, nome, email, telFixo, telCelular, ativo) 
values (mat, nome1, email1, telFixo1, telCelular1, 'S');
end$$
DELIMITER ;
DELIMITER $$
CREATE PROCEDURE listarServ()
begin
select * from funcionario, lotado where 
matricula = matFunc and dataFim is null and  senha is null and cargo is null and ativo ='S';
end$$
DELIMITER ;
DELIMITER $$
CREATE PROCEDURE procurarServ(in matricula1 bigint, out nome1 varchar(30),
out email1 varchar(30), out telFixo1 varchar(12),
out telCelular1 varchar(12), out ativo1 char(1))
begin
select nome,  email, telFixo, telCelular, ativo into nome1, email1, telFixo1, telCelular1, ativo1
from funcionario, lotado where matricula = matFunc 
and dataFim is null and  matricula = matricula1;
end$$
DELIMITER ;
DELIMITER $$
CREATE PROCEDURE procurarServNome(in nome1 varchar(30))
begin
select  *
from funcionario, lotado
where matricula = matFunc and dataFim is null and nome like concat('%', nome1, '%') and senha is null and cargo is null and ativo ='S';
end$$
DELIMITER ;


/* ////////////////////////////////////////////////////////////// */

delimiter $$
create procedure alterarSetor(in codigo1 bigint, in nome1 varchar(10),in ramal1 varchar(6))
begin
update setor
set nome = nome1, ramal = ramal1
where codigo = codigo1;
end $$
delimiter ;

delimiter $$
create procedure removerSetor(in codigo1 bigint)
begin
update setor set ativo='N' where codigo = codigo1;
end $$
delimiter ;

delimiter $$
create procedure inserirSetor(in codigo bigint,in nome varchar(50),in ramal varchar(6))
begin
insert into setor
values(codigo,nome,ramal, 'S');
end $$
delimiter ;


delimiter $$
create procedure procurarSetor(in codigo1 bigint, out nome1 varchar(10), out ramal1 varchar(6), out ativo1 char(1))
begin
select nome, ramal, ativo into nome1, ramal1, ativo1 from setor where codigo = codigo1;
end $$
delimiter ;


delimiter $$
create procedure procurarSetorNome(in nome1 varchar(10))
begin
select * from setor
where nome like concat('%', nome1, '%') and ativo='S';

end $$
delimiter ;




delimiter %%
create procedure listarSetor()
begin
select * from setor where ativo='S';
end%%
delimiter ;

delimiter %%
create procedure readmitirSetor(in cod bigint)
begin
update setor set ativo='S' where codigo = cod;
end%%
delimiter ;
 /* ////////////////////////////////////////////////////////////// */                                       
delimiter $$
create procedure adicionarVeiculo (in placa varchar(7), in cor varchar(10), 
in marca varchar(10),in modelo varchar(10), IN anoFabri smallint)                                            
begin 
insert into veiculo values(placa,cor,marca,modelo,anoFabri, 'N');
end $$
delimiter ;



delimiter $$	
create procedure pesquisarVeiculo (in placa1 varchar(7),out marca1 varchar(10),
out cor1 varchar(10),out modelo1 varchar(10), out anoFabri1 smallint)
begin 
select  * from veiculo v, funcionario f, cadastra c  
where  v.placa = c.placa and c.matProp = f.matricula;
end $$
delimiter ;  

  
delimiter $$
create procedure listar()
begin
select * from veiculo v, funcionario f, cadastra c where v.placa = c.placa and c.matProp = f.matricula and 
c.ativo = 'S' and f.ativo='S';
end $$
delimiter ;

delimiter $$
create procedure listarFiltrado(in palavra varchar(7))
begin
select * from veiculo v, funcionario f, cadastra c where v.placa = c.placa and 
c.matProp = f.matricula and v.placa like concat('%', palavra, '%')
and c.ativo = 'S' and f.ativo='S' ;
end $$
delimiter ;

delimiter $$	
create procedure veiculo (in placa1 varchar(7))
begin 
select  * from veiculo v  
where  v.placa = placa1;
end $$
delimiter ;


select * from cadastra  