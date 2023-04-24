Question 1 : 

Dans BinaryHeapTest, à la fin vous trouverez des tests (qui n'en sont pas) dont la trace répond aux premières questions.

Q1 :
INFOS: insert 10
INFOS: insert 12
INFOS: insert 1
INFOS: Percolate up value : 1 from index: 2
INFOS: insert 14
INFOS: insert 6
INFOS: Percolate up value : 6 from index: 4
INFOS: insert 5
INFOS: Percolate up value : 5 from index: 5
INFOS: insert 8
INFOS: insert 15
INFOS: insert 3
INFOS: Percolate up value : 3 from index: 8
INFOS: Percolate up value : 14 from index: 3
INFOS: insert 9
INFOS: Percolate up value : 9 from index: 9
INFOS: insert 7
INFOS: Percolate up value : 7 from index: 10
INFOS: insert 4
INFOS: Percolate up value : 4 from index: 11
INFOS: Percolate up value : 10 from index: 5
INFOS: insert 11
INFOS: insert 13
INFOS: insert 2
INFOS: Percolate up value : 2 from index: 14
INFOS: Percolate up value : 8 from index: 6

(0)1 
(1)3 (2)2 
(3)6 (4)7 (5)5 (6)4 
(7)15 (8)14 (9)12 (10)9 (11)10 (12)11 (13)13 (14)8 

Q2 : _"ici on ne donne pas de comparateur"_
INFOS: create heap with array
INFOS: Percolate down value : 8 from index: 6
INFOS: swap(13, 8)
INFOS: Percolate down value : 8 from index: 13
INFOS: After percolate down: 
[10, 12, 1, 14, 6, 5, 13, 15, 3, 9, 7, 4, 11, 8, 2]
INFOS: Percolate down value : 5 from index: 5
INFOS: swap(11, 5)
INFOS: Percolate down value : 5 from index: 12
INFOS: After percolate down: 
[10, 12, 1, 14, 6, 11, 13, 15, 3, 9, 7, 4, 5, 8, 2]
INFOS: Percolate down value : 6 from index: 4
INFOS: swap(9, 6)
INFOS: Percolate down value : 6 from index: 9
INFOS: After percolate down: 
[10, 12, 1, 14, 9, 11, 13, 15, 3, 6, 7, 4, 5, 8, 2]
INFOS: Percolate down value : 14 from index: 3
INFOS: swap(15, 14)
INFOS: Percolate down value : 14 from index: 7
INFOS: After percolate down: 
[10, 12, 1, 15, 9, 11, 13, 14, 3, 6, 7, 4, 5, 8, 2]
INFOS: Percolate down value : 1 from index: 2
INFOS: swap(13, 1)
INFOS: Percolate down value : 1 from index: 6
INFOS: swap(8, 1)
INFOS: Percolate down value : 1 from index: 13
INFOS: After percolate down: 
[10, 12, 13, 15, 9, 11, 8, 14, 3, 6, 7, 4, 5, 1, 2]
INFOS: Percolate down value : 12 from index: 1
INFOS: swap(15, 12)
INFOS: Percolate down value : 12 from index: 3
INFOS: swap(14, 12)
INFOS: Percolate down value : 12 from index: 7
INFOS: After percolate down: 
[10, 15, 13, 14, 9, 11, 8, 12, 3, 6, 7, 4, 5, 1, 2]
INFOS: Percolate down value : 10 from index: 0
INFOS: swap(15, 10)
INFOS: Percolate down value : 10 from index: 1
INFOS: swap(14, 10)
INFOS: Percolate down value : 10 from index: 3
INFOS: swap(12, 10)
INFOS: Percolate down value : 10 from index: 7
INFOS: After percolate down: 
[15, 14, 13, 12, 9, 11, 8, 10, 3, 6, 7, 4, 5, 1, 2]

(0)15 
(1)14 (2)13 
(3)12 (4)9 (5)11 (6)8 
(7)10 (8)3 (9)6 (10)7 (11)4 (12)5 (13)1 (14)2 

avec un tas MIN
(0)1
(1)3 (2)2
(3)12 (4)6 (5)4 (6)8
(7)15 (8)14 (9)9 (10)7 (11)5 (12)11 (13)13 (14)10
[1, 3, 2, 12, 6, 4, 8, 15, 14, 9, 7, 5, 11, 13, 10]

Q3 : 
INFOS: Delete extreme 1
INFOS: Percolate down value : 10 from index: 0
INFOS: swap(2, 10)
INFOS: Percolate down value : 10 from index: 2
INFOS: swap(4, 10)
INFOS: Percolate down value : 10 from index: 5
INFOS: swap(5, 10)
INFOS: Percolate down value : 10 from index: 11
INFOS: After percolate down: 
[2, 3, 4, 12, 6, 5, 8, 15, 14, 9, 7, 10, 11, 13, null]


(0)2 
(1)3 (2)4 
(3)12 (4)6 (5)5 (6)8 
(7)15 (8)14 (9)9 (10)7 (11)10 (12)11 (13)13 




