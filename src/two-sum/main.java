import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Solution {

  /**
   * Time complexity: O(n^2)
   * Space complexity: O(1)
   */
  public int[] solutionOne(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
          return new int[] { i , j };
        }
      }
    }

    throw new IllegalArgumentException("No two sum solution");
  }

  /**
   * Time complexity: O(n log n)
   * Space complexity: O(n)
   */
  public int[] solutionTwo(int[] nums, int target) {
    // 1) Create a map to store the original indexes of values in our input array
    Map<Integer, List<Integer>> orignalIndexes = new HashMap<Integer, List<Integer>>();
    for (int i = 0; i < nums.length; i++) {
      List<Integer> ids = orignalIndexes.getOrDefault(nums[i], new ArrayList<>());
      ids.add(i);
      orignalIndexes.put(nums[i], ids);
    }

    // 2) Sort the array
    Arrays.sort(nums);

    // 3) Use two pointers to converge on an valid two sum pair
    int l = 0;
    int r = nums.length - 1;
    while (l < r) {
      if (nums[l] + nums[r] == target) {
        // if the number is the same then we'd need the 0th and 1st index. Otherwise, we
        // can use the 0th index for both of our numbers in the two sum pair
        if (nums[l] == nums[r]) {
          int id1 = orignalIndexes.get(nums[l]).get(0);
          int id2 = orignalIndexes.get(nums[l]).get(1);
          return new int[] { id1, id2 };
        } else {
          int id1 = orignalIndexes.get(nums[l]).get(0);
          int id2 = orignalIndexes.get(nums[r]).get(0);
          return new int[] { id1, id2 };
        }
      } else if (nums[l] + nums[r] < target) {
        l++;
      } else {
        r--;
      }
    }

    throw new IllegalArgumentException("No two sum solution");
  }

  /**
   * Time complexity: O(n)
   * Space complexity: O(n)
   */
  public int[] solutionThree(int[] nums, int target) {
    // keep track of values we have already seen and store there index as well
    Map<Integer, Integer> valuesSeen = new HashMap<Integer, Integer>();
    for (int i = 0; i < nums.length; i++) {
        int pairVal = target-nums[i];
        // When we find come across a number whose 'pair_val' we have already seen then we have found
        // a valid two sum pair!
        if (valuesSeen.containsKey(pairVal)) {
            return new int[] { valuesSeen.get(pairVal) , i };
        }
        valuesSeen.put(nums[i],i);
    }

    throw new IllegalArgumentException("No two sum solution");
  }
}