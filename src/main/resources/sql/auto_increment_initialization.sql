SET foreign_key_checks = 0;    # 외래키 체크 설정 해제

USE zoozoo_bank;
ALTER TABLE account AUTO_INCREMENT = 1;
SET @COUNT = 0;
UPDATE account SET id = @COUNT:=@COUNT+1
WHERE id > 0;

ALTER TABLE customer AUTO_INCREMENT = 1;
SET @COUNT = 0;
UPDATE customer SET id = @COUNT:=@COUNT+1
WHERE id > 0;

ALTER TABLE bankbranch AUTO_INCREMENT = 1;
SET @COUNT = 0;
UPDATE bankbranch SET id = @COUNT:=@COUNT+1
WHERE id > 0;

SET foreign_key_checks = 1;    # 외래키 체크 설정

truncate zoozoo_bank.account;
