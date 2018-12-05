Star 1:

:%s /\v(\u\l|\l\u)&(.)\2\c//g

bind to macro q

10000000 @q


Star 2:
for each letter, remove it and run algo from star 1:
  :%s /a\c//g -> 10318
  :%s /b\c//g -> 10300
  ...
  :%s /v\c//g -> 5122

Looking at the final macro was a bit cinematic, slowly working its way down in size. Very neat.
