import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HeuristicaSimples {

    protected static final String goal = "123456780";
    protected static String estadoInicial;
    protected static List<String> visitados;
    protected static List<NodoSequencia> fronteira;
    protected static int expandidosCount = 0;
    protected static int maiorFronteira = 0;

    public static void main(String[] args) {
        System.out.println("Digite o estado inicial, inserindo 0 no lugar do espaço vazio. (Exemplo: estado final é 123456780)");
        Scanner sc = new Scanner(System.in);
        try {
            String input = sc.nextLine();
            while (!estadoValido(input)) {
                System.out.println("Estado inválido. Tente novamente.");
                input = sc.nextLine();
            }
            estadoInicial = input;
            System.out.println("Buscando...");
            buscaHeuristica();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buscaHeuristica() {
        visitados = new ArrayList<>();
        fronteira = new ArrayList<>();
        Nodo nInicial = new Nodo(estadoInicial, calculaHeuristicaSimples(estadoInicial));
        NodoSequencia nsAtual = new NodoSequencia(nInicial);

        String estadoAtual = estadoInicial;
        while(!estadoAtual.equals(goal)) {
            for(Nodo alcancavel : calculaAlcance(estadoAtual)) {
                if (!visitados.contains(alcancavel.estado)) {
                    fronteira.add(nsAtual.proximo(alcancavel));
                    expandidosCount++;
                }
            }
            if (maiorFronteira < fronteira.size()) {
                maiorFronteira = fronteira.size();
            }
            fronteira = fronteira.stream().sorted(Comparator.comparingDouble(NodoSequencia::getCustoComHeuristica).thenComparingDouble(NodoSequencia::getHeuristica)).collect(Collectors.toList());
            NodoSequencia pop = fronteira.remove(0);
            visitados.add(pop.getEstado());
            estadoAtual = pop.getEstado();
            nsAtual = pop;
        }

        System.out.println("Melhor caminho obtido, " + (nsAtual.caminhos.size()-1) + " movimentos:");
        for (Nodo caminho : nsAtual.caminhos) {
            System.out.println("---");
            System.out.println(caminho.estado.substring(0,3));
            System.out.println(caminho.estado.substring(3,6));
            System.out.println(caminho.estado.substring(6,9));
        }
        System.out.println("---");
        System.out.println("Total de nodos visitados: " + visitados.size());
        System.out.println("Total de nodos expandidos: " + expandidosCount);
        System.out.println("Maior fronteira: " + maiorFronteira);
        System.out.println("Tamanho do caminho: " + (nsAtual.caminhos.size()-1));
    }

    public static List<Nodo> calculaAlcance(String estado) {
        List<Nodo> alcance = new ArrayList<>();
        int posVazio = estado.indexOf('0');
        if (posVazio%3 != 2) { //diferente de ultima coluna
            char toMove = estado.charAt(posVazio+1);
            String direita = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(direita, calculaHeuristicaSimples(direita)));
        }
        if (posVazio%3 != 0) { //diferente de primeira coluna
            char toMove = estado.charAt(posVazio-1);
            String esquerda = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(esquerda, calculaHeuristicaSimples(esquerda)));
        }
        if (posVazio/3 != 2) { //diferente de ultima linha
            char toMove = estado.charAt(posVazio+3);
            String desce = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(desce, calculaHeuristicaSimples(desce)));
        }
        if (posVazio/3 != 0) { //diferente de primeira linha
            char toMove = estado.charAt(posVazio-3);
            String sobe = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(sobe, calculaHeuristicaSimples(sobe)));
        }

        return alcance;
    }

    public static double calculaHeuristicaSimples(String estado) {
        double sum = 0;
        for (int i = 0; i < 9; i++) {
            if (estado.charAt(i) != (char)((i+1)%9+'0'))
                sum++;
        }
        return sum;
    }

    public static boolean estadoValido(String estado) {
        return estado.length() == 9
                && estado.contains("1")
                && estado.contains("2")
                && estado.contains("3")
                && estado.contains("4")
                && estado.contains("5")
                && estado.contains("6")
                && estado.contains("7")
                && estado.contains("8")
                && estado.contains("0");
    }
}
