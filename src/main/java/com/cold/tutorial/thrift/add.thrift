namespace java com.cold.tutorial.thrift.server

typedef i32 int

service AdditionService {
    int add(1:int n1, 2:int n2)
}