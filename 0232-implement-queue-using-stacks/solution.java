import java.util.Stack;

class MyQueue {

    private Stack<Integer> input;
    private Stack<Integer> output;

    public MyQueue() {
        input = new Stack<>();
        output = new Stack<>();
    }

    public void push(int x) {
        input.push(x);
    }

    public int pop() {

        if (output.isEmpty()) {
            moveElements();
        }

        return output.pop();
    }

    public int peek() {

        if (output.isEmpty()) {
            moveElements();
        }

        return output.peek();
    }

    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }

    private void moveElements() {

        while (!input.isEmpty()) {
            output.push(input.pop());
        }
    }
}
