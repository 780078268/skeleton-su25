package src;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
/* An src.AmoebaFamily is a tree, where nodes are Amoebas, each of which can have
   any number of children. */
public class AmoebaFamily implements Iterable<AmoebaFamily.Amoeba> {
    /* ROOT is the root amoeba of this src.AmoebaFamily */
    private Amoeba root = null;

    /* Creates an src.AmoebaFamily, where the first Amoeba's name is NAME. */
    public AmoebaFamily(String name) {
        root = new Amoeba(name, null);
    }

    /* Adds a new Amoeba with CHILDNAME to this src.AmoebaFamily as the youngest
       child of the Amoeba named PARENTNAME. This src.AmoebaFamily must contain an
       Amoeba named PARENTNAME. */
    public void addChild(String parentName, String childName) {
        if (root != null) {
            root.addChildHelper(parentName, childName);
        }
    }

    /* Returns the length of the longest name in this src.AmoebaFamily. */
    public int longestNameLength() {
        if (root != null) {
            return root.longestNameLengthHelper();
        }
        return 0;
    }

    /* Returns the longest name in this src.AmoebaFamily. */
    public String longestName() {
        if (root != null) {
            return root.longestNameHelper();
        }
        return "";
    }

    /* Returns an Iterator for this src.AmoebaFamily. */
    @Nonnull
    public Iterator<Amoeba> iterator() {
        return new AmoebaDFSIterator(root);
    }

    /* Creates a new src.AmoebaFamily and prints it out. */
    public static void main(String[] args) {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");
        System.out.println("Here's the family!");
        for (Amoeba familyName : family) {
            System.out.println(familyName);
        }
    }

    /* An Amoeba is a node of an src.AmoebaFamily. */
    public static class Amoeba {
        private final String name;
        private final Amoeba parent;
        private final ArrayList<Amoeba> children;

        public Amoeba(String name, Amoeba parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ArrayList<Amoeba>();
        }

        public String toString() {
            return name;
        }

        public Amoeba getParent() {
            return parent;
        }

        public ArrayList<Amoeba> getChildren() {
            return children;
        }

        /* Adds child with name CHILDNAME to an Amoeba with name PARENTNAME. */
        public void addChildHelper(String parentName, String childName) {
            if (name.equals(parentName)) {
                Amoeba child = new Amoeba(childName, this);
                children.add(child);
            } else {
                for (Amoeba a : children) {
                    a.addChildHelper(parentName, childName);
                }
            }
        }

        /* Returns the length of the longest name between this Amoeba and its
           children. */
        public int longestNameLengthHelper() {
            int maxLengthSeen = name.length();
            for (Amoeba a : children) {
                maxLengthSeen = Math.max(maxLengthSeen, a.longestNameLengthHelper());
            }
            return maxLengthSeen;
        }

        public String longestNameHelper() {
            String maxStringSeen = name;
            for (Amoeba a : children) {
                String s = a.longestNameHelper();
                if (s.length() > maxStringSeen.length()) {
                    maxStringSeen = s;
                }
            }
            return maxStringSeen;
        }

        /* An Iterator class for the src.AmoebaFamily, running a DFS traversal on the
           src.AmoebaFamily. Complete enumeration of a family of N Amoebas should take
           O(N) operations. */

    }
    public static class AmoebaDFSIterator implements Iterator<Amoeba> {
        private final Stack<Amoeba> fringe;

        /* AmoebaDFSIterator constructor. Sets up all of the initial information
           for the AmoebaDFSIterator. */
        public AmoebaDFSIterator(Amoeba root) {
            fringe = new Stack<Amoeba>();
            if(root != null) {
                fringe.push(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Ran out of elements");
            }
            Amoeba next = fringe.pop();
            for (int i = next.children.size() - 1; i >= 0; i--) {
                if (next.children.get(i) != null) {
                    fringe.push(next.children.get(i));
                }
            }
            return next;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* An Iterator class for the src.AmoebaFamily, running a BFS traversal on the
       src.AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaBFSIterator implements Iterator<Amoeba> {
        /* AmoebaBFSIterator constructor. Sets up all of the initial information
           for the AmoebaBFSIterator. */
        private Queue<Amoeba> fringe;

        public AmoebaBFSIterator(Amoeba root) {
            fringe = new LinkedList<Amoeba>();
            if (root != null) {
                fringe.add(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Ran out of elements");
            }
            Amoeba next = fringe.remove();
            for (int i = 0; i < next.children.size(); i++) {
                if (next.children.get(i) != null) {
                    fringe.add(next.children.get(i));
                }
            }
            return next;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

