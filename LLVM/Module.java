package LLVM;

import LLVM.type.FunctionType;
import LLVM.value.BasicBlock;
import LLVM.value.Function;
import LLVM.value.GlobalVariable;
import LLVM.value.Instruction.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Module {
    protected List<GlobalVariable> GlobalVariableList;
    protected List<Function> FunctionList;

    public Module(){
        this.GlobalVariableList=new ArrayList<>();
        this.FunctionList=new ArrayList<>();
    }

    public void addFunctionList(Function value){
        FunctionList.add(value);
    }

    public void addGlobalVariableList(GlobalVariable value){
        GlobalVariableList.add(value);
    }

    @Override
    public String toString(){
        StringBuilder a=new StringBuilder();
        for(int i=0;i<5;i++){
            a.append(FunctionList.get(i).toString());
            a.append("\n");
        }
        for(Value value:GlobalVariableList){
            a.append(value.toString());
            a.append("\n");
        }
        for(int i=5;i<FunctionList.size();i++){
            a.append(FunctionList.get(i).toString());
            a.append("\n");
        }
        return a.toString();
    }

    public BasicBlock getCurBasicBlock(){
        return FunctionList.get(FunctionList.size()-1).getCurBasicBlock();
    }

    public void addNewBasicBlock(BasicBlock basicBlock){
        FunctionList.get(FunctionList.size()-1).addBasicBlock(basicBlock);
    }

    public String getCurFunctionName() {
        return FunctionList.get(FunctionList.size()-1).getName().substring(1);
    }

    public Instruction getLastInstruction(){
        return getCurBasicBlock().getLastInstruction();
    }

    public Function getCurFunction(){
        return FunctionList.get(FunctionList.size()-1);
    }
}
