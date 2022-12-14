import unittest
from main import EllipticCurve, ECPoint, Point

test_curve = EllipticCurve(-7, 10)  # y^2 = x^3 - 7*x + 10 (R)


class Test(unittest.TestCase):

    def test_add_ECPoints(self):
        # (1, 2) + (3, 4) = (-3, 2)
        self.assertEqual(ECPoint.AddECPoints(
            Point(1, 2), Point(3, 4)), Point(-3, 2))

        # (2, -2) + (3, 4) = (31, -172)
        self.assertEqual(ECPoint.AddECPoints(
            Point(2, -2), Point(3, 4)), Point(31, -172))

    def test_DoubleECPoints(self):
        # Double (1, 2) = (-1, -4)
        self.assertEqual(ECPoint.DoubleECPoints(
            Point(1, 2), curve=test_curve), Point(-1, -4))

        # Double (3, 4) = (0.25, 2.875)
        self.assertEqual(ECPoint.DoubleECPoints(
            Point(3, 4), curve=test_curve), Point(0.25, 2.875))

    def test_ScalarMult(self):
        # 5*(1, 2) = (-3.16, -0.752)
        self.assertEqual(ECPoint.ScalarMult(
            Point(1, 2), 5, curve=test_curve), Point(-3.16, -0.752))

    def test_IsOnCurveCheck(self):
        # (1, 2) is on curve
        self.assertEqual(ECPoint.IsOnCurveCheck(
            Point(1, 2), curve=test_curve), True)

        # (4, 2) is not on curve
        self.assertEqual(ECPoint.IsOnCurveCheck(
            Point(4, 2), curve=test_curve), False)


if __name__ == '__main__':
    unittest.main(verbosity=2)
