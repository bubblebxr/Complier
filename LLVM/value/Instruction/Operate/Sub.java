package LLVM.value.Instruction.Operate;


import LLVM.Type;
import LLVM.Value;
import LLVM.value.Instruction.Instruction;
import MIPS.Instruction.Memory.Li;
import MIPS.Instruction.MipsInstruction;
import MIPS.Instruction.Operate.Addu;
import MIPS.Instruction.Operate.Move;
import MIPS.Instruction.Operate.Subu;
import MIPS.MIPSGenerator;

import java.util.ArrayList;

/**
 * @className: Sub
 * @author: bxr
 * @date: 2024/11/7 20:37
 * @description: 减
 */

public class Sub extends Instruction {

    public Sub(String name, Type type) {
        super(name, type);
    }

    public Sub(String name, Type type, ArrayList<Value> operators) {
        super(name,type,operators);
    }

    @Override
    public String toString(){
        StringBuilder a=new StringBuilder();
        a.append(name);
        a.append(" = sub ");
        a.append(type.toString());
        a.append(" ");
        for(int i=0;i<operators.size();i++){
            a.append(operators.get(i).getName());
            if(i<operators.size()-1){
                a.append(",");
            }
        }
        a.append("\n");
        return a.toString();
    }

    public ArrayList<MipsInstruction> generateMips() {
        ArrayList<MipsInstruction> list = new ArrayList<>();
        String label1="",label2="";
        if(operators.get(0).getName().equals("0")){
            label1="$zero";
        }else if(operators.get(0).getName().charAt(0)!='%'){
            list.add(new Li(Integer.parseInt(operators.get(0).getName()),false));
            label1= MIPSGenerator.registerStack.pop();
            list.add(new Move(label1,"$v1"));
        }else{
            if(MIPSGenerator.irToMips.containsKey(operators.get(0).getName())){
                label1= MIPSGenerator.irToMips.get(operators.get(0).getName());
            }
        }
        if(operators.get(1).getName().equals("0")){
            label2="$zero";
        }else if(operators.get(1).getName().charAt(0)!='%'){
            list.add(new Li(Integer.parseInt(operators.get(1).getName()),false));
            label2="$v1";
        }else{
            if(MIPSGenerator.irToMips.containsKey(operators.get(1).getName())){
                label2=MIPSGenerator.irToMips.get(operators.get(1).getName());
            }
        }

        String reg=MIPSGenerator.registerStack.pop();
        list.add(new Subu(reg,label1,label2));

        MIPSGenerator.irToMips.put(name,reg);
        if(!label1.equals("$zero")&&!label1.matches("\\d+")){
            MIPSGenerator.registerStack.add(label1);
        }
        if(!label2.equals("$v1")&&!label2.equals("$zero")&&!label2.matches("\\d+")){
            MIPSGenerator.registerStack.add(label2);
        }

        return list;
    }
}
