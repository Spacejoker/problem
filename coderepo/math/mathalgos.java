int binom(int n, int k) {
	int[] res = new int[n + 1];

	for (int i = 0; i <= n; i++) {
		res[i] = 1;
		for (int j = i - 1; j > 0; j--) {
			res[j] += res[j - 1];
		}
	}
	return res[k];
}

public int GCD(int a, int b) {
	if (b == 0)
		return a;
	return GCD(b, a % b);
}

public long GCD(long a, long b) {
	if (b == 0)
		return a;
	return GCD(b, a % b);
}

private boolean[] siev(int n) {

	boolean[] prime = new boolean[n + 1];

	Arrays.fill(prime, true);
	prime[0] = false;
	prime[1] = false;

	for (int i = 2; i < prime.length; i++) {
		int nr = i;
		if (prime[nr]) {
			nr += i;
			while (nr <= n) {
				prime[nr] = false;
				nr += i;
			}
		}
	}

	return prime;
}

int[] ps;
{
	boolean[] primes = new boolean[maxn];
	Arrays.fill(primes, true);
	primes[0] = primes[1] = false;
	int psc = 0;
	for (int i = 2; i < primes.length; ++i) {
		if (primes[i]) {
			psc++;
			for (int j = 2 * i; j < primes.length; j += i) {
				primes[j] = false;
			}
		}
	}
	ps = new int[psc];
	for (int i = 0, j = 0; i < primes.length; ++i) {
		if (primes[i]) {
			ps[j++] = i;
		}
	}
}

int fi(int n) {
	int result = n;
	for (int i = 2; i * i <= n; i++) {
		if (n % i == 0)
			result -= result / i;
		while (n % i == 0)
			n /= i;
	}
	if (n > 1)
		result -= result / n;
	return result;
}
