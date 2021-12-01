package Tank_Game.Patterns.Interpreter;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import static processing.core.PApplet.println;

public class Interpreter extends Thread{
    @Override
    public void run() {
        super.run();
        readPipe();
    }

    private static void readPipe() {
        try {
            File file = new File("TanksServer" + File.separator + "console.fifo");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            ArrayList<Expression> expList = new ArrayList<Expression>();
            Expression ex = null;
            String line;
            while(true) {
                if ((line=br.readLine())!=null) {
                    ex = parse(line);
                    if (ex != null) {
                        ex.execute();
                        expList.add(ex);
                    }
                    if (line.equals("exit")) {
                        break;
                    }
                } else {
                    sleep(500);
                }
            }
            fr.close();    //closes the stream and release the rsesources
        } catch (Exception e) {
            return;
        }
    }

    private static Expression parse(String line) {
        println(line);
        StringTokenizer context = new StringTokenizer(line);
        String cur_tok = "";
        Expression ex = null;
        Stack<Expression> expStack = new Stack<Expression>();

        while(context.hasMoreTokens()){
            cur_tok = context.nextToken();

            if( Expression.isFunction(cur_tok)) {
                if (cur_tok.equals("set")) {
                    Expression new_ex = new SetExpression();
                    if (expStack.size() > 0) {
                        expStack.peek().expList.add(new_ex);
                    }
                    ex = new_ex;
                    expStack.push(ex);
                } else { //exit
                    Expression new_ex = new ExitExpression();
                    if (expStack.size() > 0) {
                        expStack.peek().expList.add(new_ex);
                    }
                    ex = new_ex;
                    expStack.push(ex);
                }

            }else if (Expression.isFE(cur_tok)){
                if (expStack.size() == 0) {
                    return null;
                }

                expStack.pop();
                ex = expStack.peek();
            } else if (Expression.isInt(cur_tok)){
                if (expStack.size() == 0){
                    return null;
                }
                Expression new_ex = new IntExpression(Integer.parseInt(cur_tok));
                ex.expList.add(new_ex);

            } else {
            if (expStack.size() == 0) {
                return null;
                }
                Expression new_ex = new StringExpression(cur_tok);
                ex.expList.add(new_ex);
            }
        }

        return expStack.firstElement();
    }

}
