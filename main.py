from dataclasses import dataclass
import random
import math 

@dataclass
class Point:
    x: float
    y: float


@dataclass
class EllipticCurve:
    a: float
    b: float


class ECPoint:

    @staticmethod
    def BasePointGGet(order: int, curve: EllipticCurve):
        """
        Think that all points on curve are equally good to be the base point
        order: int - order of subgroup
        """
        
        subgroup = []
        g = Point(0, 0)

        while True: 
            x = random.randint(1, 100000)
            y = math.sqrt(x**3 + curve.a * x + curve.b)

            if ECPoint.IsOnCurveCheck(Point(x, y), curve):
                g = Point(x, y)
                break
            
        for i in range(1, order + 1):
            subgroup.append(ECPoint.ScalarMult(g, i, curve))
        

        return g, subgroup
        

    @staticmethod
    def ECPointGen(x: float, y: float) -> Point:
        return Point(x, y)

    @staticmethod
    def IsOnCurveCheck(a: Point, curve: EllipticCurve) -> bool:
        return a.y**2 - a.x**3 - curve.a*a.x - curve.b == 0

    @staticmethod
    def AddECPoints(a: Point, b: Point) -> Point:

        m = (a.y - b.y) / (a.x - b.x)

        x_r = m*m - a.x - b.x
        y_r = a.y + m * (x_r - a.x)

        return Point(x_r, -y_r)

    @staticmethod
    def DoubleECPoints(point: Point, curve: EllipticCurve) -> Point:
        x_r = ((3 * point.x * point.x + curve.a) / (2 * point.y))**2 - 2 * point.x
        y_r = -point.y + ((3 * point.x * point.x + curve.a) /
                          (2 * point.y)) * (point.x - x_r)

        return Point(x_r, y_r)

    @staticmethod
    def ScalarMult(a: Point, k: int, curve: EllipticCurve) -> Point:

        res_point = a
        i = 1
        while i < k:
            if i * 2 > k:
                res_point = ECPoint.AddECPoints(res_point, a)
                i += 1
            else:
                res_point = ECPoint.DoubleECPoints(res_point, curve)
                i *= 2

        return res_point

    @staticmethod
    def ECPointToString(point: Point) -> str:
        return point.__str__()

    @staticmethod
    def PrintECPoint(point: Point) -> None:
        print(ECPoint.ECPointToString(point))



if __name__ == "__main__":
    test_curve = EllipticCurve(-7, 10)
    print('Curve: y^2 = x^3 - 7*x + 10 (R)\n')

    _g, _subgroup = ECPoint.BasePointGGet(10, curve=test_curve)
    print(f'Base point - {_g} \nSubgroup: {_subgroup}\n')

    print(f'(1, 2) + (3, 4) = {ECPoint.AddECPoints(Point(1,2), Point(3,4))}\n')
    
    print(f'Double (1, 2) = {ECPoint.DoubleECPoints(Point(1, 2), curve=test_curve)}\n')

    print(f'5 * (1, 2) = {ECPoint.ScalarMult(Point(1, 2), 5, curve=test_curve)}\n')

    test_point = ECPoint.ECPointGen(1, 2)
    print(f'Gen point (1, 2) -> {test_point}\n')
    ECPoint.PrintECPoint(test_point)
