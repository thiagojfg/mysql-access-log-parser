package com.ef;

public class Parser {

    public static void main(String[] args) throws ConfigFailedException {

        Config config = Config.parse(args);

        if (config.getAccessLog() != null) {
            //load acesslog file to mysql database
        }

        //search mysql records using params on config file
        //return results
    }
}
