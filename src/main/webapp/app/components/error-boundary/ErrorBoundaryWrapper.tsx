import ErrorBoundary from 'app/components/error-boundary/ErrorBoundary';
import React from 'react';

export const ErrorBoundaryWrapper = ({ ele }) => <ErrorBoundary>{ele}</ErrorBoundary>;

export default ErrorBoundaryWrapper;
