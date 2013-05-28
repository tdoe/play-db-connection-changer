play-db-connection-changer
==========================

for playframework1.2.x. with JDBC ReplicationDriver.  

jdbcのConnection.setReadOnlyをアノテーションで操作します。  
ReplicationDriverと一緒に使わないとあんまり意味無いです。

Playのコントローラクラスかコントローラクラスのメソッドに

`@DBReadOnlyTrue`  
とつけるとConnection.setReadOnly(true)を実行し  

`@DBReadOnlyFalse`  
とつけるとConnection.setReadOnly(false)を実行します。  

コントローラかメソッド単位でMasterのDBを見るかSlaveを見るかを選べます。  
Modelからは使えないので画面単位でどうぞ。
