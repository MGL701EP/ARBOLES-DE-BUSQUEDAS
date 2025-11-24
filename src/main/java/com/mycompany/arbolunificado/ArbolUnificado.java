

package com.mycompany.arbolunificado;



import java.util.ArrayList;
import java.util.List;

public class ArbolUnificado {

    // ============================================
    //            NODO GENÉRICO
    // ============================================
    private static class Nodo<T extends Comparable<T>> {
        T clave;
        Nodo<T> izquierda, derecha;

        Nodo(T clave) {
            this.clave = clave;
        }
    }

    // ============================================
    //            ÁRBOL BST GENÉRICO
    // ============================================
    static class BST<T extends Comparable<T>> {
        private Nodo<T> raiz;

        // -------- INSERTAR (CONTROL DUPLICADOS) --------
        public void insertar(T clave) {
            raiz = insertarRec(raiz, clave);
        }

        private Nodo<T> insertarRec(Nodo<T> nodo, T clave) {
            if (nodo == null) return new Nodo<>(clave);

            int cmp = clave.compareTo(nodo.clave);

            if (cmp < 0) nodo.izquierda = insertarRec(nodo.izquierda, clave);
            else if (cmp > 0) nodo.derecha = insertarRec(nodo.derecha, clave);
            else System.out.println("Clave duplicada ignorada: " + clave);

            return nodo;
        }

        // -------- BUSCAR (RETORNA PASOS) --------
        public int buscar(T clave) {
            int pasos = 0;
            Nodo<T> actual = raiz;

            System.out.print("Buscando " + clave + " | Recorrido: ");

            while (actual != null) {
                pasos++;
                System.out.print(actual.clave + " → ");

                int cmp = clave.compareTo(actual.clave);

                if (cmp == 0) {
                    System.out.println("ENCONTRADO en " + pasos + " pasos");
                    return pasos;
                }

                actual = (cmp < 0) ? actual.izquierda : actual.derecha;
            }

            System.out.println("NO ENCONTRADO en " + pasos + " pasos");
            return -1;
        }

        // -------- BÚSQUEDA (RETORNA RUTA) --------
        public List<T> rutaBusqueda(T clave) {
            List<T> path = new ArrayList<>();
            Nodo<T> actual = raiz;

            while (actual != null) {
                path.add(actual.clave);

                int cmp = clave.compareTo(actual.clave);
                if (cmp == 0) return path;

                actual = (cmp < 0) ? actual.izquierda : actual.derecha;
            }
            return path;
        }

        // -------- ELIMINAR --------
        public void eliminar(T clave) {
            raiz = eliminarRec(raiz, clave);
        }

        private Nodo<T> eliminarRec(Nodo<T> nodo, T clave) {
            if (nodo == null) return null;

            int cmp = clave.compareTo(nodo.clave);

            if (cmp < 0) nodo.izquierda = eliminarRec(nodo.izquierda, clave);
            else if (cmp > 0) nodo.derecha = eliminarRec(nodo.derecha, clave);
            else {
                if (nodo.izquierda == null) return nodo.derecha;
                if (nodo.derecha == null) return nodo.izquierda;

                Nodo<T> sucesor = minimo(nodo.derecha);
                nodo.clave = sucesor.clave;
                nodo.derecha = eliminarRec(nodo.derecha, sucesor.clave);
            }
            return nodo;
        }

        private Nodo<T> minimo(Nodo<T> nodo) {
            while (nodo.izquierda != null)
                nodo = nodo.izquierda;
            return nodo;
        }

        // -------- INORDER --------
        public void inorder() {
            inorderRec(raiz);
            System.out.println();
        }

        private void inorderRec(Nodo<T> nodo) {
            if (nodo != null) {
                inorderRec(nodo.izquierda);
                System.out.print(nodo.clave + " ");
                inorderRec(nodo.derecha);
            }
        }

        // -------- IMPRESIÓN TIPO ÁRBOL --------
        public void imprimir() {
            imprimirRec(raiz, 0);
        }

        private void imprimirRec(Nodo<T> nodo, int nivel) {
            if (nodo == null) return;

            imprimirRec(nodo.derecha, nivel + 1);
            System.out.println("   ".repeat(nivel) + nodo.clave);
            imprimirRec(nodo.izquierda, nivel + 1);
        }
    }

    // ============================================
    //                 MAIN ÚNICO
    // ============================================
    public static void main(String[] args) {

        // ------------------ ARBOL A ------------------
        System.out.println("\n=== ARBOL A (enteros) ===");

        BST<Integer> arbolA = new BST<>();
        int[] inicialA = {10, 43, 24, -10, 54, 0, 23, 82, 43};
        for (int x : inicialA) arbolA.insertar(x);

        arbolA.inorder();

        arbolA.buscar(22);
        arbolA.buscar(0);
        arbolA.buscar(24);
        arbolA.buscar(23);

        arbolA.insertar(-5);
        arbolA.insertar(-3);
        arbolA.insertar(22);
        arbolA.insertar(44);

        arbolA.inorder();

        arbolA.eliminar(10);
        arbolA.eliminar(54);
        arbolA.eliminar(82);

        arbolA.inorder();

        // ------------------ ARBOL B ------------------
        System.out.println("\n=== ARBOL B (enteros con impresión) ===");

        BST<Integer> arbolB = new BST<>();
        int[] inicialB = {32, 67, 43, 25, 52, 56, 78, 64, 23, 67};
        for (int c : inicialB) arbolB.insertar(c);

        arbolB.buscar(23);
        arbolB.buscar(24);
        arbolB.buscar(25);

        arbolB.insertar(24);
        arbolB.insertar(26);
        arbolB.insertar(27);

        arbolB.imprimir();

        // ------------------ ARBOL C ------------------
        System.out.println("\n=== ARBOL C (Strings) ===");

        BST<String> arbolC = new BST<>();
        String[] claves = {"A","Y","E","F","G","X","W","U","Z","R","B"};
        for (String s : claves) arbolC.insertar(s);

        arbolC.inorder();

        for (String key : new String[]{"U","V","W"}) {
            List<String> path = arbolC.rutaBusqueda(key);
            System.out.println("\nBuscar: " + key);
            System.out.println("Recorrido: " + path);
            System.out.println(path.get(path.size()-1).equals(key)
                    ? "Resultado: ENCONTRADO"
                    : "Resultado: NO está");
        }
    }
}
