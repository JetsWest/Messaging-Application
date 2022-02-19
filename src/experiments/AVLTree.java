package experiments;



/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V>  {
    // TODO: Implement me!

    public static int AVLsteps = 0;


    public AVLTree() {
        super();
        this.root = null;
        AVLsteps++;
    }

    public class AVLNode extends BSTNode {
        public int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
            AVLsteps++;
        }
    }


    @Override
    public V insert(K key, V value) {
        AVLsteps++;
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // Doesn't add nodes to the tree
        V oldValue = find(key);
        root = insertNode((AVLNode) root, key, value);

        return oldValue;
    }

    private AVLNode insertNode(AVLNode root, K key, V value) {
        AVLsteps++;
        if(root == null) {
            this.size++;
            return new AVLNode(key, value);
        }
        AVLNode leftChild = (AVLNode) root.children[0];
        AVLNode rightChild = (AVLNode) root.children[1];
        int direction = key.compareTo(root.key);

        if(direction < 0) { // Insert to left
            root.children[0] = insertNode(leftChild, key, value);
        } else if(direction > 0){ // Insert to right
            root.children[1] = insertNode(rightChild, key, value);
        } else { // Key already exists in tree
            root.value = value;
            return root;
        }
        updateHeight(root);
        int balance = getHeight(leftChild) - getHeight(rightChild);
        if (balance > 1) {
            // Negative if imbalance in left subtree, positive if in right subtree
            int imbalanceDirection = checkChildren(leftChild);
            if(imbalanceDirection < 0) { // Left-left
                root = rotateRight(root);
            } else if (imbalanceDirection > 0) { // Left-right
                root.children[0] = rotateLeft(leftChild);
                root = rotateRight(root);
            }
        } else if (balance < -1) {
            int imbalanceDirection = checkChildren(rightChild);
            if(imbalanceDirection > 0) { // Right-right
                root = rotateLeft(root);
            } else if (imbalanceDirection < 0) { // Right-left
                root.children[1] = rotateRight(rightChild);
                root = rotateLeft(root);
            }
        }

        return root;
    }

    private void updateHeight(AVLNode current) {
        AVLsteps++;
        current.height = 1 + Math.max(getHeight((AVLNode) current.children[0]), getHeight((AVLNode) current.children[1]));
    }

    private int getHeight(AVLNode current) {
        AVLsteps++;
        if(current == null) {
            return -1;
        } else {
            return current.height;
        }
    }

    private int checkChildren(AVLNode current) {
        AVLsteps++;
        int leftChildHeight = getHeight((AVLNode) current.children[0]);
        int rightChildHeight = getHeight((AVLNode) current.children[1]);
        return rightChildHeight - leftChildHeight;
    }

    private AVLNode rotateRight(AVLNode current) {
        AVLsteps++;
        AVLNode currentLeft = (AVLNode) current.children[0];
        AVLNode currentLeftRight = (AVLNode) currentLeft.children[1];
        currentLeft.children[1] = current;
        current.children[0] = currentLeftRight;
        updateHeight(current);
        updateHeight(currentLeft);
        return currentLeft;
    }

    private AVLNode rotateLeft(AVLNode current) {
        AVLsteps++;
        AVLNode currentRight = (AVLNode) current.children[1];
        AVLNode currentRightLeft = (AVLNode) currentRight.children[0];
        currentRight.children[0] = current;
        current.children[1] = currentRightLeft;
        updateHeight(current);
        updateHeight(currentRight);
        return currentRight;
    }
}
