import React from 'react';
import { Box, Button, Stack, Typography } from '@mui/material';
import { CustomSection } from 'app/components/CustomSection';

const PageNotFound = () => (
  <CustomSection sx={{ py: 25, backgroundColor: '#fff' }}>
    <Stack direction="column" alignItems="center" textAlign="center" spacing={3} py={1}>
      <Typography variant="h2">404</Typography>
      <Typography variant="h4" mb={2}>
        We can not find the page you are looking for
      </Typography>
      <Button variant="contained" href="/">
        Return to Homepage
      </Button>
    </Stack>
  </CustomSection>
);

export default PageNotFound;
