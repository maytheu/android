export interface PayloadOutput<T> {
  loading?: boolean;
  value?: T;
  error?: Error | null;
}
