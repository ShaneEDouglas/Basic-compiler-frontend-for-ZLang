package org.example.Token;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    protected String sourceCode;
    protected int current;
    protected int line;
    protected List<Token> tokens;

    public Tokenizer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.current = 0;
        this.line = 1;
        this.tokens = new ArrayList<Token>();
    }



    public List<Token> tokenize() {
        while (!isAtEnd()) {
            scanTokens();

        }
        // Addign an End of file token if there are no tokens
        Token eofToken = new Token(TokenType.EOF, "");
        tokens.add(eofToken);
        return tokens;
    }

    private void scanTokens() {

        skipWhiteSpaceAndComments();
        readString();
        readIdentiferOrKeyword();






    }

    private void readString() {
        // Reads and recordsa new string and sets the token for it


        StringBuilder foundWord = new StringBuilder();

        // skip the opening quote
        advance();

        while (!isAtEnd()) {





                while (peek() != '"' && !isAtEnd()) {

                    // Handle new lines for tracking

                    if (peek() == '\n') {
                        line++;
                    }

                    foundWord.append(advance());


                }

                if (isAtEnd()) {
                    System.out.println("Unterminated string at line " + line);
                    return;
                }

                advance();

            Token stringToken = new Token(TokenType.STRING_LITERAL, foundWord.toString());
            // add it to the list of tokens
            tokens.add(stringToken);

        }
    }

    private void readIdentiferOrKeyword() {

    }

    private void skipWhiteSpaceAndComments() {
        while (!isAtEnd()) {
            char c  = sourceCode.charAt(current);

            if (c == ' ' || c == '\r' || c == '\n') {
                current++;
            } else if (c == '\n') {
                line++;
                current++;
            } else if  (c == '/' && peekNext() == '/') {
                // This will be a comment

                while (peek() != '\n' && !isAtEnd()){
                    current++;
                }
            } else {
                break; // stop when there is no more whitespace or comments
            }
        }
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return sourceCode.charAt(current);
    }

    private boolean isAtEnd() {
        return this.current == this.sourceCode.length();
    }

    private char peekNext() {
        return sourceCode.charAt(current + 1);
    }

    private char advance() {
        return sourceCode.charAt(current++);
    }

}
