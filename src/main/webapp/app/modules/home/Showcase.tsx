import { Box } from '@mui/material';
import React from 'react';
import './showcase.scss';

const Showcase = React.memo(() => (
  <Box className="showcase" sx={{ py: 50 }}>
    {/* The video container */}
    <div className="showcase__wrapper">
      <video loop playsInline autoPlay className="showcase__video" src="./../../../assets/video/showcase.mp4"></video>
    </div>
    {/* The content */}
    <div className="showcase__content">Content!</div>
  </Box>
));

export default Showcase;
