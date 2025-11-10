package org.example;

import org.example.Symbol.Symbol;
import org.example.Symbol.SymbolTable;
import org.example.Token.Token;
import org.example.Token.TokenType;
import org.example.Token.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String sourceCodeFilePath = Files.readString(Path.of("src/main/java/org/example/Zlangexamplefile.txt"));
        Tokenizer tokenizer = new Tokenizer(sourceCodeFilePath);
        List<Token> tokens = tokenizer.tokenize();

//        System.out.println("\n" + "tokens: " + "\n");
//        for (Token token : tokens) {
//            System.out.println(token.getType() + ": " + token.getLexeme());
//        }

        // Symbol table

        SymbolTable symbolTable = new SymbolTable();

        // For making declarations
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            if (token.getType() == TokenType.LIT) {
                Token nameToken = tokens.get(i + 1); // Identifier
                Token equalToken = tokens.get(i + 2); // =
                Token valueToken = tokens.get(i + 3); // The number or string literals will be held in ths index (counter = 0)



                if (nameToken.getType() == TokenType.IDENTIFIER && equalToken.getType() == TokenType.EQUAL) {

                    String varname = nameToken.getLexeme();
                    String varType = "lit";
                    String varValue = valueToken.getLexeme();


                    symbolTable.declare(varname, varType, varValue);
                }
            }
        }
        symbolTable.printInitial();

        //For making assignments and updating
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            // You will get the the identifer (lit) check for the '=' and grab the value token after
            if (token.getType() == TokenType.IDENTIFIER && tokens.get(i +1).getType() == TokenType.EQUAL) {
                String varname = token.getLexeme();
                Token valueToken = tokens.get(i +2);

                // get the token immdiately after  '='
                String newValue = valueToken.getLexeme();

                symbolTable.assign(varname, newValue);
            }
        }

        symbolTable.printUpdated();

    }
}