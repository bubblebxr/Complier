package frontend.AST;

import frontend.Token.TrueToken;

import java.util.ArrayList;
import java.util.List;

public class AddExp {
    protected List<MulExp> mulExpList;
    /*加减符号存储*/
    protected List<TrueToken> tokenList;

    public AddExp(){
        this.mulExpList=new ArrayList<>();
        this.tokenList=new ArrayList<>();
    }

    public void insertMulExpList(MulExp mulExp){
        this.mulExpList.add(mulExp);
    }

    public void insertTokenList(TrueToken token){
        this.tokenList.add(token);
    }

    public String outputAddExp() {
        StringBuilder a=new StringBuilder();
//        a.append(mulExpList.get(0).outputMulExp());
//        if(mulExpList.size()!=1){
//            a.append("<AddExp>\n");
//        }
//        for(int i=1;i<mulExpList.size();i++){
//            a.append(tokenList.get(i-1).toString());
//            a.append(mulExpList.get(i).outputMulExp());
//        }
        for(int i=0;i<mulExpList.size();i++){
            a.append(mulExpList.get(i).outputMulExp());
            if(i<tokenList.size()){
                a.append("<AddExp>\n");
                a.append(tokenList.get(i).toString());
            }
        }
        a.append("<AddExp>\n");
        return a.toString();
    }

    public List<MulExp> getMulExpList() {
        return mulExpList;
    }
}
//addExp +mulExp +mulExp
