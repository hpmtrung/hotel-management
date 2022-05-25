import { SvgIcon, Tab, TabProps, Tabs, TabsProps, useMediaQuery } from '@mui/material';
import React from 'react';

export interface ResponsiveTabProps extends TabsProps {
  breakpoint: string;
  iconSVG?: boolean;
  tabProps?: TabProps;
  tabItems: { icon?: TabProps['icon']; label: React.ReactNode }[];
}

export const ResponsiveTab = ({ breakpoint, value, onChange, tabItems, iconSVG = false, ...other }: ResponsiveTabProps) => {
  const scroll = useMediaQuery(breakpoint);

  let tabsProps: TabsProps = { onChange, value };
  if (scroll) {
    tabsProps = { ...tabsProps, variant: 'fullWidth', centered: true, ...other };
  } else {
    tabsProps = { ...tabsProps, variant: 'scrollable', scrollButtons: true, allowScrollButtonsMobile: true, ...other };
  }

  return (
    <Tabs {...tabsProps}>
      {tabItems.map((item, index) => (
        <Tab
          key={index}
          icon={iconSVG ? <SvgIcon component={item.icon as React.ElementType<any>} inheritViewBox /> : item.icon}
          label={item.label}
          iconPosition="start"
        />
      ))}
    </Tabs>
  );
};
