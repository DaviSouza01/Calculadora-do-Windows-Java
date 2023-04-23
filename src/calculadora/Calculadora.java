package calculadora;

import java.math.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class Calculadora{
    
    private boolean virgula = false, repetir = false, reset = false, apagar = false, 
    visibilidadeMemoria = true;
    private String num1 = "", num2 = "", operacao = ""; 
    private int op = 0;   
    
    public void mudarDisplay(JLabel display, String bApertado, JLabel historico){
        
        if(reset){ // se o usuário apertar qualquer número depois de uma operação ser finalizada
            limparTela(display, historico); // a tela será resetada
        }
        
        String tela = display.getText(); // a variável tela recebe o conteúdo do visor
        
        if(tela.equals("0")){ // se o visor só conter um 0
            tela = ""; // o visor será limpo
        }
        
        tela += bApertado; // o visor recebe o que tem nele mais o botão apertado
        
        if(tela.equals(",")){ // se só conter uma vírgula no visor
           tela = "0,"; // acrescenta um 0 a esquerda dela
        }
        
        display.setText(tela); // escreve no visor o conteúdo após colocar o botão apertado
        
    }
    
    public void removerCaractere(JLabel display, JLabel historico){
        
        if(apagar){ // usuário acabou de efetuar uma operação, e clicou no botão de apagar
            historico.setText(""); // o histórico é apagado
            num1 = ""; // num1 volta a ser vazio
        }else{ // se o usuário deseja remover o último caractere à direita do visor
           
            String tela = display.getText();
        
            if(tela.substring((tela.length() - 1)).equals(",")){
                // se o último caractere for uma vírgula...
                virgula = false; // usuário pode inserir novamente a vírgula quando desejar
            }

            tela = tela.substring(0, tela.length() - 1); // último caractere é removido
            
            if(tela.length() == 0){ 
                // se ao remover o último caractere do visor não sobrar nada...
                display.setText("0"); // escreve um 0
            }else{ 
                // se ao remover o último caractere sobrar algo...
                display.setText(tela); // escreve no visor 
            }
        }       
    }
    
    public void mudarVirgula(JLabel display, String bApertado, JLabel historico){
        if(!virgula){ // se não tiver vírgula no visor
           mudarDisplay(display, bApertado, historico); // chama a função para escrever a vírgula
           virgula = true; // variável booleana afirmando que tem vírgula no visor
        }
    }
    
    public void bC(JLabel display, JLabel historico){
        display.setText("0"); // limpa o visor e coloca um 0
        historico.setText(""); // limpa o histórico
        num1 = ""; // apaga o primeiro número
        num2 = ""; // apaga o segundo número
        virgula = false; // vírgula pode ser colocada novamente
        repetir = false; // encerra as repetições do igual
        apagar = false; // o botão de apagar voltará a apagar os caracteres
    }
    
    public void MaisMenos(JLabel display){
        String tela = display.getText();
        // converte o conteúdo de visor para decimal, trocando as vírgulas por ponto
        float n = Float.parseFloat(tela.replace(",", "."));
        if(n != 0){
            if(tela.startsWith("-")){ // se o valor do visor for negativo
                tela = tela.substring(1); // remove o sinal negativo
            }else{ // se o valor do visor for positivo
                tela = "-" + tela; // insere o sinal negativo antes do número
            }
            display.setText(tela);
        }
    }
    
    public void bMais(JLabel display, JLabel historico){
        if("".equals(num1)){ // se o primeiro número ainda não foi armazenado
            System.out.println("entrou no IF1");
            num1 = display.getText(); // armazena o número do visor em num1
            if(num1.contains(",")){ 
                // retira os zeros a direita desnecessários e remove a vírgula caso preciso
                num1 = num1.replaceFirst("0*$", "").replaceFirst("\\,$", "");
            }
            if("-0".equals(num1)){ // caso num1 seja -0...
                num1 = "0"; // retira o sinal negativo
            }
        }else if(repetir){ 
            // se o usuário deseja fazer uma operação com o resultado da repetição do igual
            System.out.println("entrou no IF2");
            num1 = display.getText(); // num1 recebe o resultado da repetição do igual
        }else{
            System.out.println("Entrou no ELSE");
            if(!"0".equals(display.getText())){ // se o display tiver algo além de 0
                System.out.println("entrou no tem algo no display \n");
                calcular(display, op, historico); // faz o cálculo com a operação escolhida
                num1 = display.getText(); // num1 recebe o resultado da operação
            }
        }    
        
        historico.setText(num1 + " + "); // escreve o primeiro número mais o símbolo +
        display.setText("0"); // reseta o display para 0
        op = 1; // define que a operação é a de adição
        operacao = "+"; // define a variável simbólica para +
        //reseta as variáveis booleanas necessárias
        virgula = false;
        apagar = false;
        reset = false;
        repetir = false;
    }
    
    public void bMenos(JLabel display, JLabel historico){
        // a mesma lógica do botão bMais é utilizada, mas a operação que será 
        // feita é a de subtração
        if("".equals(num1)){
            num1 = display.getText();
            if(num1.contains(",")){
                num1 = num1.replaceFirst("0*$", "").replaceFirst("\\,$", "");
            }
            if("-0".equals(num1)){
                num1 = "0";
            }
        }else if(repetir){
            num1 = display.getText();
        }else{
            System.out.println("Entrou no ELSE");
            if(!"0".equals(display.getText())){
                System.out.println("entrou no tem algo no display \n");
                calcular(display, op, historico);
                num1 = display.getText();
            }
        }
        display.setText("0");
        historico.setText(num1 + " - ");
        op = 2;
        operacao = "-";
        virgula = false;
        apagar = false;
        reset = false;
        repetir = false;
    }
    
    public void bX(JLabel display, JLabel historico){
        // a mesma lógica do botão bMais é utilizada, mas a operação que será 
        // feita é a de multiplicação
        if("".equals(num1)){
            System.out.println("entrou no IF1");
            num1 = display.getText();
            if(num1.contains(",")){
                num1 = num1.replaceFirst("0*$", "").replaceFirst("\\,$", "");
            }
            if("-0".equals(num1)){
                num1 = "0";
            }
        }else if(repetir){
            System.out.println("entrou no IF2");
            num1 = display.getText();
        }else{
            System.out.println("Entrou no ELSE");
            if(!"0".equals(display.getText())){
                System.out.println("entrou no tem algo no display \n");
                calcular(display, op, historico);
                num1 = display.getText();
            }
        }
        
        display.setText("0");
        historico.setText(num1 + " x ");
        op = 3;
        operacao = "x";
        virgula = false;
        apagar = false;
        reset = false;
        repetir = false;
    }
    
    public void bCE(JLabel display, JLabel historico){
        if(repetir){ // se o usuário apertar no bCE após ter feito repetições do igual
            display.setText("0"); //reseta o visor para 0
            historico.setText(""); // reseta o histórico
            virgula = false; // a vírgula poderá ser inserida
            repetir = false; // a repetição é interrompida, caso esteja sendo feita
        }else{ // caso o usuário aperte bCE em qualquer outra situação
            display.setText("0"); // reseta o visor para 0
            virgula = false; // vírgula poderá ser inserida novamente
        }
    }
    
    public void bDividir(JLabel display, JLabel historico){
        // a mesma lógica do botão bMais é utilizada, mas a operação que será 
        // feita é a de divisão
        if("".equals(num1)){
            System.out.println("entrou no if");
            num1 = display.getText();
            if(num1.contains(",")){
                num1 = num1.replaceFirst("0*$", "").replaceFirst("\\,$", "");
            }
            if("-0".equals(num1)){
                num1 = "0";
            }
        }else if(repetir){
            System.out.println("entrou no 2 if");
            num1 = display.getText();
        }else{
            System.out.println("Entrou no ELSE");
            if(!"0".equals(display.getText())){
                System.out.println("entrou no tem algo no display \n");
                calcular(display, op, historico);
                num1 = display.getText();
            }
        }
        
        display.setText("0");
        historico.setText(num1 + " ÷ ");
        op = 4;
        operacao = "÷";
        virgula = false;
        apagar = false;
        reset = false;
        repetir = false;
    }
    
    public void bIgual(JLabel display, JLabel historico){
        if(!"".equals(num1)){ // caso o primeiro número já tenha sido armazenado...
            System.out.println("repetir: " + repetir);
            // na próxima vez que o usuário apertar o botão de apagar...
            apagar = true; // somente o histórico será apagado
            calcular(display, op, historico); // chama a função que fará os cálculos
            repetir = true; // caso o igual seja apertado novamente, será uma repetição
            
            System.out.println("apagar: " + apagar);
            System.out.println("num1: " + num1);
            System.out.println("num2: " + num2);
            System.out.println("op: " + op);
            System.out.println("repetir: " + repetir);
            System.out.println("---------------------------");
        }
    
    }
    
    public void bXAoQuadrado(JLabel display, JLabel historico){
        reset = true; // próximo clique em botão numérico resetará o visor
        String n1;
        String tela = display.getText().replace(",", ".");
        if(tela.contains(".")){ // caso o valor do visor seja decimal...
            // são removidos quaisquer zeros desnecessários á direita da vírgula
            tela = tela.replaceFirst("0*$", "").replaceFirst("\\.$", "");
        }
        if("-0".equals(tela)){ // caso o valor do visor, após as remoções, seja "-0"
            tela = "0"; // trata o dado, removendo o sinal negativo
        } 
        n1 = tela; // reserva o valor que será calculado em n1
        
        BigDecimal n = new BigDecimal(tela);// o conteúdo do visor é convertido e armazenado
        n = n.multiply(n); // n recebe n elevado ao quadrado (n²)
        DecimalFormat df = new DecimalFormat("#.######"); // define a formatação do resultado
        tela = df.format(n); // formata o resultado para apenas 6 casas decimais
        if(tela.contains(".")){ // repete a verificação por zeros à direira desnecessários
            tela = tela.replaceFirst("0*$", "").replaceFirst("\\.$", "");
        }
        display.setText(tela.replace(".",",")); // insere o resultado no visor
        historico.setText("sqr("+n1.replace(".", ",")+")"); // insere a operação no histórico
    }
    
    public void bUmSobreX(JLabel display, JLabel historico){
        String tela = display.getText(); // reserva o conteúdo do visor em uma variável String
        if(tela.contains(",")){ // remove os zeros à direta da vírgula desnecessários, caso haja...
            tela = tela.replaceFirst("0*$", "").replaceFirst("\\,$", "");
        }
        if("-0".equals(tela)){ // caso o valor do visor seja "-0"
            tela = "0"; // faz o tratamento do dado, removendo o sinal negativo
        }
        if(!"0".equals(tela)){ // caso o valor seja diferente de 0
            tela = display.getText().replace(",", ".");//faz a conversão da vírgula
            if(tela.contains(".")){ // remove os zeros desnecessários à direita da vírgula
                tela = tela.replaceFirst("0*$", "").replaceFirst("\\.$", "");
            }
            String n1 = tela; // reserva o valor do visor em uma variável
            BigDecimal n = new BigDecimal(tela); // faz a conversão de tela para decimal
            // faz a divisão de 1 com n, aproxima para cima e armazena na variável tela
            tela = String.valueOf(BigDecimal.ONE.divide(n, 10, RoundingMode.HALF_UP));
            DecimalFormat df = new DecimalFormat("#.#######"); // padrão com 7 casas decimais
            tela = df.format(new BigDecimal(tela)); // formata a variável tela com o novo padrão
            
            if(tela.contains(".")){ // remove os zeros desbecessários à direita da vírgula
                tela = tela.replaceFirst("0*$", "").replaceFirst("\\.$", "");
            }
            display.setText(tela.replace(".", ","));//escreve o resultado no visor
            historico.setText("1/" + n1.replace(".", ",")); // define o histórico
        }else{ // caso o valor do visor seja 0, a operação não poderá ser realizada
            // informa ao usuário que não é possível realizar a operação
            historico.setText("Operação inválida!");
        }
        reset = true; // próximo clique em um botão numérico resetará o visor
    }
    
    public void bRaizDeX(JLabel display, JLabel historico){
        // reserva o conteúdo do visor em uma variável String
        String tela = display.getText().replace(",",".");
        if(tela.contains(".")){ // remove os zeros à direta da vírgula desnecessários, caso haja...
            tela = tela.replaceFirst("0*$", "").replaceFirst("\\.$", "");
        }
        if("-0".equals(tela)){ // caso o valor do visor seja "-0"
            tela = "0"; // faz o tratamento do dado, removendo o sinal negativo
        }
        BigDecimal n1 = new BigDecimal(tela); // conversão do conteúdo do visor para decimal
        if(n1.compareTo(BigDecimal.ZERO) >= 0){ // faz o cálculo caso n1 seja maior ou igual a 0
            // faz o cálculo da raiz de n1, com 9 casas decimais de precisão
            BigDecimal raiz = n1.sqrt(new MathContext(9));
            DecimalFormat df = new DecimalFormat("#.########"); // define a formatação
            tela = df.format(raiz); // aplica a formatação e armazera em tela
            if(tela.contains(".")){ // remove os zeros desnecessários à direita da vírgula
                tela = tela.replaceFirst("0*$", "").replaceFirst("\\.$", "");
            }
            // escreve o resultado no visor, convertendo "." para ","
            display.setText(tela.replace(".", ","));
            tela = String.valueOf(n1); // tela recebe o valor que havia sido digitado para efetuar o cálculo
            if(tela.contains(".")){ // remove quaisquer zeros desnecessários à direita da vírgula
                tela = tela.replaceFirst("0*$", "").replaceFirst("\\.$", "");
            }
            historico.setText("√" + tela.replace(".",",")); // define o histórico
        }else{ // caso o valor do visor seja negativo...
            // informa ao usuário que a operação não poderá ser realizada
            historico.setText("Operação inválida!");
        }
        reset = true; // próximo clique em um botão numérico resetará o visor
    }
    
    public void bPercent(JLabel display, JLabel historico){
        String tela = display.getText(); // armazena o conteúdo do visor
        if(tela.contains(",")){ // remove os zeros desnecessários à direita da vírgula
            tela = tela.replaceFirst("0*$", "").replaceFirst("\\,$", "");
        }
        if("-0".equals(tela)){ // caso o conteúdo do visor seja "-0"...
            tela = "0"; // faz o tratamento do dado e remove o sinal negativo
        }
        historico.setText(tela + " %"); // define o histórico
        // converte o conteúdo do visor para decimal, e armazena em n
        BigDecimal n = new BigDecimal(tela.replace(",", "."));
        // res recebe o resultado da divisão de n por 100
        BigDecimal res = n.divide(new BigDecimal("100"));
        // converte o valor numérico para String
        tela = String.valueOf(res).replace(".", ",");
        if(tela.contains(",")){ // remove os zeros desnecessários à direita da vírgula
            tela = tela.replaceFirst("0*$", "").replaceFirst("\\,$", "");
        }
        display.setText(tela); // escreve o conteúdo de tela no visor
    }
    
    public void limparTela(JLabel display, JLabel historico){
        display.setText(""); // reseta o visor
        historico.setText(""); // reseta o histórico
        virgula = false; // vírgula poderá ser utilizada novamente
        repetir = false; // repetição do igual é cancelada, caso esteja ocorrendo
        reset = false; // variável reset volta para falso
        apagar = false; // próximo clique no botão de apagar apaga o visor e não apenas o histórico
        num2 = ""; // reseta o primeiro número
        num1 = ""; // reseta o segundo número
        op = 0; // reseta a operação para o estado inicial
    }
    
    public void mMais(JLabel memoria, JLabel display){
        if("".equals(memoria.getText())){ // caso não tenha nada na JLabel memória...
            if(!"0".equals(display.getText())){ // caso o valor do visor seja diferente de 0
                String tela = display.getText(); // armazena o conteúdo do visor em tela
                if(tela.contains(",")){ // remove os zeros desnecessários à direita da vírgula
                    tela = tela.replaceFirst("0*$", "").replaceFirst("\\,$", "");
                }
                if("-0".equals(tela)){ // caso o valor do visor seja "-0"...
                    tela = "0"; // faz o tratamento do dado, removendo o sinal negativo
                }
                memoria.setText(tela); // armazena o conteúdo de tela na memória
                reset = true; // próximo clique em botões numéricos resetará o visor
            }
        }else{ // caso algum valor já tenha sido armazenado na memória...
            // armazena-se o conteúdo do visor, convertido para decimal, na variável n
            BigDecimal n = new BigDecimal(display.getText().replace(",", "."));
            // armazena-se o conteúdo da memória, convertido para decimal, na variável m
            BigDecimal m = new BigDecimal(memoria.getText().replace(",", "."));
            // armazena o resultado da adição de m com n na variável res
            String res = String.valueOf(m.add(n));
            if(res.contains(".")){ // faz a remoção dos zeros desnecessários à direita da vírgula
                res = res.replaceFirst("0*$", "").replaceFirst("\\.$", "");
            }
            // insere o resultado na memória
            memoria.setText(res.replace(".", ","));
            reset = true; // próximo clique em botões numéricos resetará o visor
        }
    }
    
    public void mMenos(JLabel memoria, JLabel display){
        /*A mesma lógica do botão mMais é aplicada neste método, porém a operação do else será 
        a de subtração*/
        if("".equals(memoria.getText())){
            if(!"0".equals(display.getText())){
                String tela = display.getText();
                if(tela.contains(",")){
                    tela = tela.replaceFirst("0*$", "").replaceFirst("\\,$", "");
                }
                if("-0".equals(tela)){
                    tela = "0";
                }
                memoria.setText(tela);
                reset = true;
            }
        }else{
            BigDecimal n = new BigDecimal(display.getText().replace(",", "."));
            BigDecimal m = new BigDecimal(memoria.getText().replace(",", "."));
            String res = String.valueOf(m.subtract(n)); 
            if(res.contains(".")){
                res = res.replaceFirst("0*$", "").replaceFirst("\\.$", "");
            }
            memoria.setText(res.replace(".", ","));
            reset = true;
        }
    }
    
    public void mC(JLabel memoria, JLabel ms){
        // caso algum valor já tenha sido armazenado na memória..
        if(!"".equals(memoria.getText())){
            memoria.setText(""); // o conteúdo da memória é apagado
            ms.setText(""); // o conteúdo de ms é apagado
            reset = true; // próximo clique em qualquer botão numérico resetará o visor
        } 
    }
    
    public void mR(JLabel memoria, JLabel display, JLabel historico){
        // caso algum valor já tenha sido armazenado na memória..
        if(!"".equals(memoria.getText())){
            // insere o conteúdo armazenado na memória no visor
            display.setText(memoria.getText());
            historico.setText(""); // reseta o histórico
            reset = true;// próximo clique em qualquer botão numérico resetará o visor  
        } 
    }
    
    public void mSeta(JLabel memoria, JLabel ms){
        if(visibilidadeMemoria){ // caso a memória e o ms estejam sendo exibidos
            memoria.setVisible(false); // a memória não é mais exibida
            ms.setVisible(false);// o ms não é mais exibido
            visibilidadeMemoria = false;
        }else{ // caso a memória e o ms não estejam sendo exibidos
            memoria.setVisible(true); // a memória volta a ser exibida
            ms.setVisible(true); // o ms volta a ser exibido
            visibilidadeMemoria = true;
        }  
    }
    
    public void mS(JLabel memoria, JLabel ms){
        String conteudo = memoria.getText(); // armazena o conteúdo da memória na variável conteudo
        // adiciona o conteúdo da memória ao conteúdo já existente em ms
        conteudo += "  " + ms.getText();
        ms.setText(conteudo); // insere o conteúdo da JLabel ms
        reset = true; // próximo clique em botões numéricos resetará o visor
    }
    
    public void calcular(JLabel display, int op, JLabel historico){
        if(repetir){ // caso esteja ocorrendo uma repetição do igual
            num1 = display.getText(); // o primeiro número recebe o conteúdo do visor
        }else{ // caso seja a primeira vez acionando o método calcular()...
            num2 = display.getText(); // o conteúdo do visor é armazenado no segundo número
            if(num2.contains(",")){ // faz a remoção dos zeros desnecessários à direita da vírgula
                num2 = num2.replaceFirst("0*$", "").replaceFirst("\\,$", "");
            }
        }
        reset = true; // próximo clique em botões numéricos resetará o visor
        
        System.out.println("num1: " + num1);
        System.out.println("num2: " + num2);
        System.out.println("operacao: " + operacao);
        System.out.println("-----------------");
        
        // converte o primeiro número para um valor decimal
        BigDecimal n1 = new BigDecimal(num1.replace(",", "."));
        // converte o segundo número para um valor decimal
        BigDecimal n2 = new BigDecimal(num2.replace(",", "."));
        // inicia uma variável com 0 para armazenar o resultado temporário
        BigDecimal resultadoTemporario = BigDecimal.ZERO;
        // inicia a String que receberá o resultado final das operações
        String resultadoFinal;
        
        // define o histórico para exibir a operação realizada para o usuário
        historico.setText(num1.replace(".", ",") + " " + operacao + " " + num2.replace(".", ","));
        
        // ocorre a verificação para efetuar a operação desejada pelo usuário
        switch (op) {
            case 1 -> resultadoTemporario = n1.add(n2); // adição
            case 2 -> resultadoTemporario = n1.subtract(n2); // subtração
            case 3 -> resultadoTemporario = n1.multiply(n2); // multiplicação
            case 4 -> { // divisão
                if(n2.compareTo(BigDecimal.ZERO) != 0){ // caso o segundo número não seja zero
                    resultadoTemporario = n1.divide(n2, 10, RoundingMode.HALF_UP); // definindo a escala e o modo de arredondamento
                }else{ // caso o divisor seja 0...
                    resultadoTemporario = BigDecimal.ZERO; // resultado recebe 0
                    // informa para o usuário que a operação não pode ser realizada
                    historico.setText("Operação inválida!");
                }
            }
        }

        System.out.println("Resultado temporário: " + resultadoTemporario);
        /*armazena o resultado temporário no resultado final, removendo os zeros à direita 
        desnecessários*/
        resultadoFinal = resultadoTemporario.stripTrailingZeros().toPlainString();

        /*Para segurança, caso o resultado seja NaN ou Infinity, o visor é resetado para 0
        e o usuário é informado que a operação é inválida*/
        if("NaN".equals(resultadoFinal) || "Infinity".equals(resultadoFinal)) {
            display.setText("0"); // display é resetado
            historico.setText("Operação inválida!"); // histórico é definido
        }else{ // caso seja um valor numérico válido
            // insere o resultado da operação no visor, convertendo "." para ","
            display.setText(resultadoFinal.replace(".", ","));
        }
    }  
}