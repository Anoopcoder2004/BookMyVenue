export type ToastType = 'success' | 'error' | 'warning' | 'info';

export interface Toast {

  title: string;

  message: string;

  type: ToastType;

  duration?: number;

  closable?: boolean;

}