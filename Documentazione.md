

You have two choices.

    Linearly search an unordered list. This is O(N).
    Linearly search an ordered list. This is also O(N) but it is twice as fast, as on average the item you search for will be in the middle, and you can stop there if it isn't found.

You don't have the choice of binary searching it, as you don't have direct access to elements of a linked list.