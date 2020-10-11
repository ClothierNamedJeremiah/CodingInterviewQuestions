# Solution

The following document contains solutions and detailed explanations for the following problem: [1589. Maximum Sum Obtained of Any Permutation](https://leetcode.com/problems/maximum-sum-obtained-of-any-permutation/)

## Notes to Reader

I have chosen to include multiple solutions which result in Time Limit Exceeded (TLE), because I believe it is important to teach others how one has arrived at a solution. Additionally, only pseudocode is provided for TLE solutions.

I do not want to replicate existing documentation, therefore I would have included links to existing documentation on LeetCode that explains [Permutations](https://leetcode.com/problems/permutations/solution/). I have also made the assumption that readers are familiar with Permutations.

## Approach 1: Brute Force (TLE)

A Naive solution would be to simply do what the problem is asking us to do.

> Return the _maximum total sum_ of all requests among __all permutations__ of nums

### Solution Steps

Steps:
1. Generate all permutations of `nums`.
2. For each permutation calculate the `total sum` and keeping track of the `maximum total sum` we have seen.
3. Return the `maximum total sum`.

```{python}
def maxSumRangeQuery(nums, requests):
  # Step 1)
  permutations = generatePermutations(nums)

  # Step 2)
  max_sum = 0
  for permutation in permutations
    sum = 0
    for req in requests:
      for i = req[0] upto req[1]
        sum += nums[i]
    max_sum = max(max_sum, sum)

  # Step 3)
  return max_sum
```

### Time and Space Complexity
|Term|Description|
|-|-|
|`N`| the size of `nums`|
|`R`| the size of `requests`|

__Time Complexity:__ (N! * RN), N! to generate all the permutations and for each permutation we iterate through all of the requests.

__Space Complexity:__ O(N!), to store all the permutations generated.

## Approach 2: Preprocessing Requests (TLE)

One popular technique used to improve run time complexity is to preprocess the input data you're given. In other words, transform the input data you're given to a more useful format, which increases the run time of a specific step in your algorithm.

Notice in our first solution, that for each permutation we iterate over all of our requests. __Is there a way to preprocess our requests and eliminate one of the for-loops in our first solution?__ It turns out there is :)

We're going to map our `requests` input from a two-dimensional array to a one-dimensional array, which we will call `hit_counter`. To create the `hit_counter` we initialize an empty array with zeros, then for each request we increment all the values in the requests by 1.

### Solution Steps

The steps for the new solution are as follows:
1. Preprocess the `requests` and create a `hit_counter`.
2. Generate all permutations of `nums`.
3. For each permutation calculate the `total sum` and keeping track of the `maximum total sum` we have seen.
4. Return the `maximum total sum`.

```
def maxSumRangeQuery(nums, requests):
  # Step 1)
  hit_counter = new int[len(nums)]
  for req in requests:
    for i = req[0] upto req[1]
      hit_counter[i]++

  # Step 2)
  permutations = generatePermutations(nums)

  # Step 3)
  max_sum = 0
  for permutation in permutations
    sum = 0
    for req in requests:
      for i = req[0] upto req[1]
        sum += nums[i]
    max_sum = max(max_sum, sum)

  # Step 4)
  return max_sum
```

### Time and Space Complexity

|Term|Description|
|-|-|
|`N`| the size of `nums` |
|`R`| the size of `requests` |

__Time Complexity:__ (N! + RN), N! to generate all the permutations and RN to preprocess the requests data

__Space Complexity:__ O(N!), to store all the permutations generated

We have added an additional step at the beginning, but we have increased our time complexity

## Approach 3: Better Preprocessing (TLE)

We can build the `hit_counter` mentioned in the second solution much faster.

Imagine we're given an input where the requests have very large ranges. For example:

```{python}
  requests = [
    [1, 99999],
    [1, 75000],
    [20000, 105000],
  ]
```

If we were to iterate through all those requests and following __Approach 2__ it would require: 100000, 75000, and 85000 iterations for the first, seconds, and third request respectively. That's a total of 260000 iterations to build our `hit_counter`.

Instead of iterating through requests one-by-one and incrementing values of our `hit_counter` by 1, we can log the start and end points of each request and then after we have logged all the start and end points, we can iterate through our `hit_counter` incrementing values.

### Solution Steps

The steps for the new solution are as follows:
1. Create a `hit_counter` and for every `request` log start and end points.
2. Iterate through the `hit_counter` and increment values.
3. Generate all permutations of `nums`.
4. For each permutation calculate the `total sum` and keeping track of the `maximum total sum` we have seen.
5. Return the `maximum total sum`.

```{python}
def maxSumRangeQuery(nums, requests):
  # Step 1)
  hit_counter = new int[len(nums)]
  for req in requests:
    hit_counter[req[0]]++
      if (req[1] + 1 < len(nums)) then
        hit_counter[req[1] + 1]--

  # Step 2)
  for i in range(1, len(nums)):
    hit_counter[i] += hit_counter[i-1]

  # Step 3)
  permutations = generatePermutations(nums)

  # Step 4)
  max_sum = 0
  for permutation in permutations
    sum = 0
    for req in requests:
      for i = req[0] upto req[1]
        sum += nums[i]
    max_sum = max(max_sum, sum)

  # Step 5)
  return max_sum
```

### Time and Space Complexity

|Term|Description|
|-|-|
|`N`| the size of `nums` |
|`R`| the size of `requests` |

__Time Complexity:__ (N! + R + N), N! to generate all the permutations and R + N to preprocess the requests data

__Space Complexity:__ O(N!), to store all the permutations generated

We have improved our preprocessing step from `O(RN)` to `O(R + N)`, which is huge improvement!

## Approach 4: Preprocessing and Sorting (Optimal Solution)

Now that we have established the idea of a `hit_counter` let's improve our algorithm and get rid of the part where we calculate all the permutations.

The three previous approaches were all bounded by N! because we were generating all the permutations of `nums`. To achieve a better run time complexity better we'll have to eliminate this step.

For the following inputs we would obtain the `hit_counter` outlined below.
```
nums = [1,2,3,4,5]
requests = [[1,3], [0,1]]
hit_counter = [1,2,1,1,0]
```

To maximize the `sum` we need to find the values: `a, b, c, d, and e` which are in `nums` and will maximize the following equation:

`sum = (a * 1) + (b * 2) + (c * 1) + (d * 1) + (e * 0)`

Note: the values in the equation above (`1, 2, 1, 1, 0`) are taken from the `hit_counter`.

This equation simplifies the equation immensely! The new question we are trying to solve is: __What is the maximum sum that can obtain from multiplying pairs of values from `hit_counter` and `nums`?__

### Solution Steps

The steps for the new solution are as follows:
1. Create a `hit_counter` and for every `request` log start and end points.
2. Iterate through the `hit_counter` and increment values.
3. Sort both `nums` and `hit_counter`.
4. Iterate through `nums` and `hit_counter`, multiplying values with the same index.
5. Return the `maximum total sum`, don't forget to modulo your answer :)

```{python}
MOD = 1000000000 + 7;

def maxSumRangeQuery(nums, requests):
  # Step 1)
  hit_counter = new int[len(nums)]
  for req in requests:
    hit_counter[req[0]]++
      if (req[1] + 1 < len(nums)) then
        hit_counter[req[1] + 1]--

  # Step 2)
  for i in range(1, len(nums)):
    hit_counter[i] += hit_counter[i-1]

  # Step 3)
  nums.sort()
  hit_counter.sort()

  # Step 4)
  sum = 0
  for i in range(len(nums)):
    sum += nums[i] * hit_counter[i]

  # Step 5)
  return sum % MOD
```

### Time and Space Complexity

Term|Description|
|-|-|
|`N`| the size of `nums` |

__Time Complexity:__ (N log N), to sort `nums` and `hit_counter`

__Space Complexity:__ O(N), to store `hit_counter`

