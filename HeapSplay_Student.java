package HeadSplay;

import java.util.*;

public class HeapSplay_Student {
	
	    // ---------------------------
	    // HEAP: MIN-HEAP (Urgent-first)
	    // ---------------------------
	    static class MinHeap {
	        private final List<Integer> a = new ArrayList<>();

	        public void insert(int x) { a.add(x); siftUp(a.size() - 1); }

	        public int extractMin() {
	            if (a.isEmpty()) throw new NoSuchElementException("MinHeap empty");
	            int min = a.get(0);
	            int last = a.remove(a.size() - 1);
	            if (!a.isEmpty()) { a.set(0, last); siftDown(0); }
	            return min;
	        }

	        private void siftUp(int i) {
	            while (i > 0) {
	                int p = (i - 1) / 2;
	                if (a.get(p) <= a.get(i)) break;
	                swap(p, i); i = p;
	            }
	        }
	        private void siftDown(int i) {
	            int n = a.size();
	            while (true) {
	                int l = 2 * i + 1, r = 2 * i + 2, s = i;
	                if (l < n && a.get(l) < a.get(s)) s = l;
	                if (r < n && a.get(r) < a.get(s)) s = r;
	                if (s == i) break;
	                swap(i, s); i = s;
	            }
	        }
	        private void swap(int i, int j) {
	            int t = a.get(i); a.set(i, a.get(j)); a.set(j, t);
	        }
	    }

	    // ---------------------------
	    // HEAP: MAX-HEAP (Largest-first)
	    // ---------------------------
	    static class MaxHeap {
	        private final List<Integer> a = new ArrayList<>();
	        public void insert(int x) { a.add(x); siftUp(a.size() - 1); }
	        public int extractMax() {
	            if (a.isEmpty()) throw new NoSuchElementException("MaxHeap empty");
	            int mx = a.get(0);
	            int last = a.remove(a.size() - 1);
	            if (!a.isEmpty()) { a.set(0, last); siftDown(0); }
	            return mx;
	        }

	        private void siftUp(int i) {
	            while (i > 0) {
	                int p = (i - 1) / 2;
	                if (a.get(p) >= a.get(i)) break;
	                swap(p, i); i = p;
	            }
	        }
	        private void siftDown(int i) {
	            int n = a.size();
	            while (true) {
	                int l = 2 * i + 1, r = 2 * i + 2, b = i;
	                if (l < n && a.get(l) > a.get(b)) b = l;
	                if (r < n && a.get(r) > a.get(b)) b = r;
	                if (b == i) break;
	                swap(i, b); i = b;
	            }
	        }
	        private void swap(int i, int j) {
	            int t = a.get(i); a.set(i, a.get(j)); a.set(j, t);
	        }
	    }

	    // ---------------------------
	    // SPLAY TREE (Frequently accessed records faster)
	    // ---------------------------
	    static class SplayTree {
	        static class Node {
	            int key; Node left, right, parent;
	            Node(int k) { key = k; }
	        }
	        private Node root;

	        public void insert(int key) {
	            Node z = root, p = null;
	            while (z != null) {
	                p = z;
	                if (key < z.key) z = z.left;
	                else if (key > z.key) z = z.right;
	                else { splay(z); return; } // already exists
	            }
	            Node n = new Node(key); n.parent = p;
	            if (p == null) root = n;
	            else if (key < p.key) p.left = n; else p.right = n;
	            splay(n);
	        }

	        public boolean search(int key) {
	            Node z = root, last = null;
	            while (z != null) {
	                last = z;
	                if (key < z.key) z = z.left;
	                else if (key > z.key) z = z.right;
	                else { splay(z); return true; }
	            }
	            if (last != null) splay(last); // splay last accessed
	            return false;
	        }

	        private void rotateLeft(Node x) {
	            Node y = x.right; if (y == null) return;
	            x.right = y.left; if (y.left != null) y.left.parent = x;
	            y.parent = x.parent;
	            if (x.parent == null) root = y;
	            else if (x == x.parent.left) x.parent.left = y;
	            else x.parent.right = y;
	            y.left = x; x.parent = y;
	        }

	        private void rotateRight(Node x) {
	            Node y = x.left; if (y == null) return;
	            x.left = y.right; if (y.right != null) y.right.parent = x;
	            y.parent = x.parent;
	            if (x.parent == null) root = y;
	            else if (x == x.parent.left) x.parent.left = y;
	            else x.parent.right = y;
	            y.right = x; x.parent = y;
	        }

	        private void splay(Node x) {
	            while (x.parent != null) {
	                if (x.parent.parent == null) {
	                    // Zig
	                    if (x.parent.left == x) rotateRight(x.parent); else rotateLeft(x.parent);
	                } else if (x.parent.left == x && x.parent.parent.left == x.parent) {
	                    // Zig-zig
	                    rotateRight(x.parent.parent); rotateRight(x.parent);
	                } else if (x.parent.right == x && x.parent.parent.right == x.parent) {
	                    // Zig-zig
	                    rotateLeft(x.parent.parent); rotateLeft(x.parent);
	                } else if (x.parent.left == x && x.parent.parent.right == x.parent) {
	                    // Zig-zag
	                    rotateRight(x.parent); rotateLeft(x.parent);
	                } else {
	                    // Zig-zag
	                    rotateLeft(x.parent); rotateRight(x.parent);
	                }
	            }
	        }
	    }

	 // ===== Ship Cargo Scheduling After Tsunami =====
	    public static void main(String[] args) {

	        System.out.println("===== INDIVIDUAL TASK: SHIP LOGISTICS =====");

	        // ---- Min-Heap: Shortest nautical distance ----
	        MinHeap distance = new MinHeap();
	        distance.insert(25);  // Port A – 25 nm
	        distance.insert(15);  // Port B – 15 nm  (closest)
	        distance.insert(35);  // Port D – 35 nm
	        System.out.println("[Min-Heap] Next closest port distance: "
	                           + distance.extractMin() + " nm");

	        // ---- Max-Heap: Highest cargo demand ----
	        MaxHeap cargo = new MaxHeap();
	        cargo.insert(120);  // Port A demand = 120 tons
	        cargo.insert(80);   // Port B demand = 80 tons
	        cargo.insert(60);   // Port C demand = 60 tons
	        System.out.println("[Max-Heap] Highest cargo demand: "
	                           + cargo.extractMax() + " tons");

	        // ---- Splay Tree: Frequently accessed port IDs ----
	        SplayTree ports = new SplayTree();
	        ports.insert(101);  // Port A
	        ports.insert(205);  // Port B
	        ports.insert(303);  // Port D
	        boolean found = ports.search(205); // Access Port B
	        System.out.println("[Splay] Port 205 (B) found? " + found + " (splayed to root)");
	    }
}
